import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

import 'package:walletticket_app/bloc/ticket/ticket_bloc.dart';
import 'package:walletticket_app/models/ticket/ticket_response.dart';
import 'package:walletticket_app/repository/ticket_repository/ticket_repository.dart';
import 'package:walletticket_app/repository/ticket_repository/ticket_repository_impl.dart';
import 'package:walletticket_app/ui/widgets/error_page.dart';
import 'package:walletticket_app/ui/widgets/shimmer_vertical_list.dart';

void main() {
  runApp(const FavoriteTicketScreen());
}

class FavoriteTicketScreen extends StatelessWidget {
  const FavoriteTicketScreen({Key? key}) : super(key: key);

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
    ticketRepository = TicketRepositoryImpl();
    _ticketBloc = TicketBloc(ticketRepository)
      ..add(const FetchTicketByUserAndFavorite());
    super.initState();
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
              color: Colors.grey[200],
              child: Center(
                child: Column(
                  children: [
                    Column(
                      children: [
                        RefreshIndicator(
                            onRefresh: () async {
                              _ticketBloc
                                  .add(const FetchTicketByUserAndFavorite());
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
              context
                  .watch<TicketBloc>()
                  .add(const FetchTicketByUserAndFavorite());
            },
          );
        } else if (state is TicketFavoriteFetched) {
          return _createTicketView(context, state.ticket);
        } else {
          return const Text('Not support');
        }
      },
    );
  }

  Widget _createTicketView(
      BuildContext context, List<TicketResponse> listTicket) {
    return SingleChildScrollView(
      child: SizedBox(
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
      ),
    );
  }

  Widget _createTicketViewItem(BuildContext context, TicketResponse ticket) {
    return InkWell(
      // onTap: () => Navigator.push(
      //     context,
      //     MaterialPageRoute(
      //         builder: (context) => DetailTicketScreen(ticket: ticket))),
      onTap: () => showModalBottomSheet(
          isScrollControlled: true,
          context: context,
          builder: (context) => Center(
                child: Column(
                  children: [
                    Row(
                      children: [
                        Container(
                          margin: const EdgeInsets.only(
                              top: 25, left: 20, right: 120, bottom: 30),
                          child: Text(
                            ticket.title,
                            style: const TextStyle(
                                fontSize: 24,
                                fontWeight: FontWeight.w800,
                                color: Colors.black),
                          ),
                        ),
                        IconButton(
                          icon: const Icon(Icons.cancel_outlined),
                          iconSize: 30,
                          onPressed: () => Navigator.pop(context),
                        ),
                      ],
                    ),
                    Row(
                      children: [
                        Container(
                            margin: const EdgeInsets.symmetric(horizontal: 20),
                            child: Text(ticket.description))
                      ],
                    ),
                    Container(
                      margin: const EdgeInsets.only(top: 20),
                      child: Row(
                        children: [
                          Container(
                            margin: const EdgeInsets.symmetric(horizontal: 20),
                            child: const Text(
                              'Fecha de compra:',
                              style: TextStyle(
                                  fontSize: 16,
                                  fontWeight: FontWeight.w800,
                                  color: Colors.black),
                            ),
                          ),
                          Text(ticket.dateInit),
                        ],
                      ),
                    ),
                    Container(
                      margin: const EdgeInsets.only(top: 20),
                      child: Row(
                        children: [
                          Container(
                            margin: const EdgeInsets.only(right: 5),
                            child: Row(
                              children: [
                                Container(
                                  margin:
                                      const EdgeInsets.only(left: 20, right: 5),
                                  child: const Text(
                                    'Compañía:',
                                    style: TextStyle(
                                        fontSize: 16,
                                        fontWeight: FontWeight.w800,
                                        color: Colors.black),
                                  ),
                                ),
                                Text(ticket.companyName),
                              ],
                            ),
                          ),
                          Row(
                            children: [
                              Container(
                                margin:
                                    const EdgeInsets.only(left: 7, right: 5),
                                child: const Text(
                                  'Valor:',
                                  style: TextStyle(
                                      fontSize: 16,
                                      fontWeight: FontWeight.w800,
                                      color: Colors.black),
                                ),
                              ),
                              Text(ticket.price.toString()),
                            ],
                          ),
                        ],
                      ),
                    ),
                    Container(
                      margin: const EdgeInsets.only(top: 20),
                      child: Row(
                        children: [
                          Container(
                            margin: const EdgeInsets.only(right: 7),
                            child: Row(
                              children: [
                                Container(
                                  margin:
                                      const EdgeInsets.only(left: 20, right: 5),
                                  child: const Text(
                                    'Categoría:',
                                    style: TextStyle(
                                        fontSize: 16,
                                        fontWeight: FontWeight.w800,
                                        color: Colors.black),
                                  ),
                                ),
                                Text(ticket.categoryName),
                              ],
                            ),
                          ),
                          Row(
                            children: [
                              Container(
                                margin:
                                    const EdgeInsets.only(left: 10, right: 5),
                                child: const Text(
                                  'Garantía:',
                                  style: TextStyle(
                                      fontSize: 16,
                                      fontWeight: FontWeight.w800,
                                      color: Colors.black),
                                ),
                              ),
                              Text(ticket.dateEnd),
                            ],
                          ),
                        ],
                      ),
                    ),
                    Container(
                      margin: const EdgeInsets.only(top: 40),
                      child: Row(
                        children: [
                          Column(
                            children: [
                              const Text(
                                'Ticket',
                                style: TextStyle(
                                    fontSize: 16,
                                    fontWeight: FontWeight.w800,
                                    color: Colors.black),
                              ),
                              Image.network(
                                ticket.ticketImage,
                                height: 200,
                                width: 190,
                              )
                            ],
                          ),
                          Column(
                            children: [
                              const Text(
                                'Producto',
                                style: TextStyle(
                                    fontSize: 16,
                                    fontWeight: FontWeight.w800,
                                    color: Colors.black),
                              ),
                              Image.network(
                                ticket.productImage,
                                height: 200,
                                width: 190,
                              )
                            ],
                          )
                        ],
                      ),
                    ),
                    GestureDetector(
                      onTap: () {},
                      child: Container(
                          height: 45,
                          width: 140,
                          margin: const EdgeInsets.only(top: 40),
                          decoration: BoxDecoration(
                              color: Colors.red[400],
                              borderRadius: BorderRadius.circular(18)),
                          child: GestureDetector(
                            onTap: () {
                              setState(() {
                                ticketRepository.deleteTicket(ticket.id);
                                Navigator.push(
                                    context,
                                    MaterialPageRoute(
                                        builder: (context) =>
                                            const FavoriteTicketScreen()));
                              });
                            },
                            child: Row(
                              children: [
                                IconButton(
                                  icon: const Icon(
                                    Icons.delete_outlined,
                                    color: Colors.white,
                                  ),
                                  iconSize: 30,
                                  onPressed: () => Navigator.pop(context),
                                ),
                                Text(
                                  'Eliminar'.toUpperCase(),
                                  style: const TextStyle(color: Colors.white),
                                  textAlign: TextAlign.center,
                                ),
                              ],
                            ),
                          )),
                    ),
                  ],
                ),
              )),
      child: SingleChildScrollView(
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
                      borderRadius: const BorderRadius.only(
                          bottomLeft: Radius.circular(20),
                          topLeft: Radius.circular(20)),
                      child: Image.network(ticket.productImage),
                    ),
                    Positioned(
                      top: 65,
                      left: 10,
                      child: GestureDetector(
                        onTap: () {
                          if (ticket.isFavorite) {
                            ticketRepository.deleteFavorite(ticket.id);
                          } else {
                            ticketRepository.addFavorite(ticket.id);
                          }
                          Navigator.push(
                              context,
                              MaterialPageRoute(
                                  builder: (context) =>
                                      const FavoriteTicketScreen()));
                        },
                        child: Icon(
                          Icons.favorite_border_outlined,
                          color: ticket.isFavorite ? Colors.red : Colors.white,
                        ),
                      ),
                    )
                  ],
                ),
                Column(
                  children: [
                    SizedBox(
                      width: 210,
                      height: 30,
                      child: Row(
                        children: [
                          Container(
                            margin: const EdgeInsets.only(top: 5.0, left: 10.0),
                            child: Text(
                              ticket.title,
                              style: const TextStyle(
                                  fontSize: 15,
                                  color: Colors.black,
                                  fontWeight: FontWeight.w500),
                            ),
                          ),
                        ],
                      ),
                    ),
                    SizedBox(
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
                                borderRadius: const BorderRadius.all(
                                    Radius.circular(15.0)),
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
                                    child: const Icon(
                                      Icons.favorite_border_outlined,
                                    ),
                                  ),
                                  Flexible(
                                    child: Text(
                                      ticket.categoryName,
                                      softWrap: true,
                                      overflow: TextOverflow.clip,
                                      maxLines: 1,
                                    ),
                                  ),
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
                                    child: const Icon(
                                      Icons.info_outlined,
                                      color: Colors.white,
                                      size: 15,
                                    ),
                                  ),
                                  //Text(ticket.categoryName, softWrap: true),
                                  const Text("Más de 3 meses",
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
                                    child: const Icon(
                                      Icons.euro_outlined,
                                      color: Colors.black,
                                      size: 15,
                                    ),
                                  ),
                                  //Text(ticket.categoryName, softWrap: true),
                                  Text(ticket.price.toString(),
                                      style: const TextStyle(
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
      ),
    );
  }
}
