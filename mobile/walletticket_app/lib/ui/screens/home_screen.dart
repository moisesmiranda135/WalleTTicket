import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';

import 'package:http/http.dart' as http;
import 'package:walletticket_app/bloc/ticket/ticket_bloc.dart';
import 'package:walletticket_app/models/ticket/ticket_response.dart';
import 'package:walletticket_app/repository/ticket_repository/ticket_repository.dart';
import 'package:walletticket_app/repository/ticket_repository/ticket_repository_impl.dart';
import 'package:walletticket_app/styles/styles.dart';
import 'package:walletticket_app/ui/widgets/error_page.dart';
import 'package:walletticket_app/ui/widgets/shimmer_vertical_list.dart';

void main() {
  runApp(const HomeScreen());
}

class HomeScreen extends StatelessWidget {
  const HomeScreen({Key? key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        // This is the theme of your application.
        //
        // Try running your application with "flutter run". You'll see the
        // application has a blue toolbar. Then, without quitting the app, try
        // changing the primarySwatch below to Colors.green and then invoke
        // "hot reload" (press "r" in the console where you ran "flutter run",
        // or simply save your changes to "hot reload" in a Flutter IDE).
        // Notice that the counter didn't reset back to zero; the application
        // is not restarted.
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key, required this.title}) : super(key: key);

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  late TicketRepository ticketRepository;
  late TicketBloc _ticketBloc;

  @override
  void initState() {
    super.initState();
    ticketRepository = TicketRepositoryImpl();
    _ticketBloc = TicketBloc(ticketRepository)..add(const FetchTicketByUser());
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return MultiBlocProvider(
        providers: [
          BlocProvider(create: (context) => _ticketBloc),
        ],
        child: Scaffold(
          body: SingleChildScrollView(
            child: Container(
              color: Colors.grey[300],
              child: Center(
                child: Column(
                  children: [
                    Column(
                      children: [
                        Container(
                            child: Text(
                          "Todos los Tickets",
                          style: TextStyle(
                              fontSize: 24,
                              fontWeight: FontWeight.w800,
                              color: Colors.black38),
                        )),
                        RefreshIndicator(
                            onRefresh: () async {
                              _ticketBloc.add(const FetchTicketByUser());
                            },
                            child: SizedBox(
                              height: MediaQuery.of(context).size.height,
                              child: SingleChildScrollView(
                                  physics:
                                      const AlwaysScrollableScrollPhysics(),
                                  child: _createTicket(context)),
                            ))
                      ],
                    ),
                  ],
                ),
              ),
            ),
          ),
        ));
  }

  Widget _createTicket(BuildContext context) {
    return BlocBuilder<TicketBloc, TicketState>(
      bloc: _ticketBloc,
      builder: (context, state) {
        if (state is TicketInitial) {
          return const ShimmerVerticalList();
        } else if (state is TicketFetchError) {
          return ErrorPage(
            message: state.message,
            retry: () {
              context.watch<TicketBloc>().add(const FetchTicketByUser());
            },
          );
        } else if (state is TicketFetched) {
          return _createTicketView(context, state.ticket);
        } else {
          return const Text('Not support');
        }
      },
    );
  }

  Widget _createTicketView(
      BuildContext context, List<TicketResponse> listTicket) {
    return SizedBox(
      height: MediaQuery.of(context).size.height / 1.2,
      child: ListView.separated(
          itemCount: listTicket.length,
          itemBuilder: (BuildContext context, int index) {
            return _createTicketViewItem(context, listTicket[index]);
          },
          padding: const EdgeInsets.only(top: 4.0, bottom: 16),
          scrollDirection: Axis.vertical,
          shrinkWrap: true,
          separatorBuilder: (context, index) => const Divider(
                color: Colors.transparent,
                height: 10,
              )),
    );
  }

  Widget _createTicketViewItem(BuildContext context, TicketResponse ticket) {
    return Expanded(
      child: Center(
        child: Container(
          width: 375,
          height: 100,
          decoration: BoxDecoration(
              borderRadius: BorderRadius.circular(20), color: Colors.white),
          margin: const EdgeInsets.only(top: 40),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.start,
            children: [
              Stack(
                children: [
                  ClipRRect(
                    borderRadius: BorderRadius.only(
                        bottomLeft: Radius.circular(20),
                        topLeft: Radius.circular(20)),
                    child: Image.network(ticket.productImage),
                  ),
                  Positioned(
                    top: 65,
                    left: 10,
                    child: Icon(
                      Icons.favorite_border_outlined,
                      color: Colors.white,
                    ),
                  )
                ],
              ),
              Column(
                children: [
                  Container(
                    width: 210,
                    height: 30,
                    child: Row(
                      children: [
                        Container(
                          margin: const EdgeInsets.only(top: 5.0, left: 10.0),
                          child: Text(
                            ticket.title,
                            style: TextStyle(
                                fontSize: 15,
                                color: Colors.black,
                                fontWeight: FontWeight.w500),
                          ),
                        ),
                      ],
                    ),
                  ),
                  Container(
                    width: 210,
                    height: 40,
                    child: Row(
                      children: [
                        Container(
                          width: 40,
                          height: 40,
                          margin: const EdgeInsets.only(
                              top: 5.0, left: 5.0, right: 5),
                          child: ClipRRect(
                            child: Image.network(ticket.companyImage),
                          ),
                        ),
                        Container(
                            width: 150,
                            decoration: BoxDecoration(
                              border: Border.all(color: Colors.black),
                              borderRadius:
                                  BorderRadius.all(Radius.circular(15.0)),
                            ),
                            margin: const EdgeInsets.only(
                              top: 5.0,
                            ),
                            child: Row(
                              children: [
                                Container(
                                  margin: const EdgeInsets.only(
                                    left: 10.0,
                                    right: 5.0,
                                  ),
                                  child: Icon(
                                    Icons.favorite_border_outlined,
                                  ),
                                ),
                                //Text(ticket.categoryName, softWrap: true),
                                Text("proban..."),
                              ],
                            )),
                      ],
                    ),
                  ),
                  SizedBox(
                    width: 210,
                    height: 30,
                    child: Row(
                      children: [
                        Container(
                            height: 20,
                            width: 140,
                            color: Colors.green,
                            margin: const EdgeInsets.only(
                              top: 10.0,
                            ),
                            child: Row(
                              children: [
                                Container(
                                  margin: const EdgeInsets.only(
                                    left: 10.0,
                                    right: 5.0,
                                  ),
                                  child: Icon(
                                    Icons.info_outlined,
                                    color: Colors.white,
                                    size: 15,
                                  ),
                                ),
                                //Text(ticket.categoryName, softWrap: true),
                                Text("Más de 3 meses",
                                    style: TextStyle(color: Colors.white)),
                              ],
                            )),
                        Container(
                            height: 20,
                            width: 70,
                            margin: const EdgeInsets.only(
                              top: 10.0,
                            ),
                            child: Row(
                              children: [
                                Container(
                                  margin: const EdgeInsets.only(
                                    left: 5.0,
                                    right: 3.0,
                                  ),
                                  child: Icon(
                                    Icons.euro_outlined,
                                    color: Colors.black,
                                    size: 15,
                                  ),
                                ),
                                //Text(ticket.categoryName, softWrap: true),
                                Text(ticket.price.toString(),
                                    style: TextStyle(
                                        color: Colors.black,
                                        fontSize: 14,
                                        fontWeight: FontWeight.w700)),
                              ],
                            )),
                      ],
                    ),
                  ),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }
}
