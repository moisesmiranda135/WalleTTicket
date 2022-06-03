import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';

import 'package:http/http.dart' as http;
import 'package:walletticket_app/styles/styles.dart';
import 'package:walletticket_app/ui/screens/home_screen.dart';

void main() {
  runApp(const CreateTicketScreen());
}

class CreateTicketScreen extends StatelessWidget {
  const CreateTicketScreen({Key? key}) : super(key: key);

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
  final _formKey = GlobalKey<FormState>();
  TextEditingController emailController = TextEditingController();

  @override
  void initState() {
    //items = fetchUpComingList();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: SingleChildScrollView(
      child: Container(
        child: Center(
          child: Column(
            children: [
              Row(
                children: [
                  Container(
                    width: 10,
                  ),
                  IconButton(
                    icon: Icon(Icons.west_outlined),
                    iconSize: 50,
                    onPressed: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(builder: (context) => HomeScreen()),
                      );
                    },
                  ),
                  Container(
                    margin: const EdgeInsets.symmetric(horizontal: 50),
                    child: const Text(
                      "Nuevo Ticket",
                      style: TextStyle(
                          fontSize: 24,
                          fontWeight: FontWeight.w800,
                          color: Colors.black),
                    ),
                  ),
                  IconButton(
                    icon: Icon(Icons.add_outlined),
                    iconSize: 50,
                    onPressed: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(
                            builder: (context) => CreateTicketScreen()),
                      );
                    },
                  ),
                ],
              ),
              Container(
                margin: const EdgeInsets.only(top: 20),
                width: WalleTTicketStyle.formCreateFieldWidth,
                height: WalleTTicketStyle.formFieldHeight,
                decoration: const BoxDecoration(
                  color: Colors.white,
                ),
                child: TextFormField(
                  textAlignVertical: TextAlignVertical.bottom,
                  controller: emailController,
                  onSaved: (String? value) {
                    // This optional block of code can be used to run
                    // code when the user saves the form.
                  },
                  validator: (String? value) {
                    return (value == null || !value.contains('@'))
                        ? 'Escribe un email correcto'
                        : null;
                  },
                  decoration: InputDecoration(
                      isDense: true,
                      contentPadding: EdgeInsets.all(10),
                      errorBorder: WalleTTicketStyle.formBorder,
                      focusedErrorBorder: WalleTTicketStyle.formBorder,
                      enabledBorder: WalleTTicketStyle.formBorder,
                      focusedBorder: WalleTTicketStyle.formBorder,
                      fillColor: Colors.black,
                      hintText: "Escribe tu email",
                      hintStyle: const TextStyle(color: Colors.grey)),
                ),
              ),
              Container(
                margin: const EdgeInsets.only(top: 20),
                width: WalleTTicketStyle.formCreateFieldWidth,
                height: WalleTTicketStyle.formFieldHeight,
                decoration: const BoxDecoration(
                  color: Colors.white,
                ),
                child: TextFormField(
                  textAlignVertical: TextAlignVertical.bottom,
                  controller: emailController,
                  onSaved: (String? value) {
                    // This optional block of code can be used to run
                    // code when the user saves the form.
                  },
                  validator: (String? value) {
                    return (value == null || !value.contains('@'))
                        ? 'Escribe un email correcto'
                        : null;
                  },
                  decoration: InputDecoration(
                      isDense: true,
                      contentPadding: EdgeInsets.all(10),
                      errorBorder: WalleTTicketStyle.formBorder,
                      focusedErrorBorder: WalleTTicketStyle.formBorder,
                      enabledBorder: WalleTTicketStyle.formBorder,
                      focusedBorder: WalleTTicketStyle.formBorder,
                      fillColor: Colors.black,
                      hintText: "Descripción del Producto",
                      hintStyle: const TextStyle(color: Colors.grey)),
                ),
              ),
              Container(
                margin: const EdgeInsets.only(top: 20),
                child: Row(
                  children: [
                    Container(
                      margin: const EdgeInsets.only(left: 37, right: 16),
                      width: 145,
                      height: WalleTTicketStyle.formFieldHeight,
                      decoration: const BoxDecoration(
                        color: Colors.white,
                      ),
                      child: TextFormField(
                        textAlignVertical: TextAlignVertical.bottom,
                        controller: emailController,
                        onSaved: (String? value) {
                          // This optional block of code can be used to run
                          // code when the user saves the form.
                        },
                        decoration: InputDecoration(
                            isDense: true,
                            contentPadding: EdgeInsets.all(10),
                            errorBorder: WalleTTicketStyle.formBorder,
                            focusedErrorBorder: WalleTTicketStyle.formBorder,
                            enabledBorder: WalleTTicketStyle.formBorder,
                            focusedBorder: WalleTTicketStyle.formBorder,
                            fillColor: Colors.black,
                            hintText: "Fecha de compra",
                            hintStyle: const TextStyle(color: Colors.grey)),
                      ),
                    ),
                    Container(
                      margin: const EdgeInsets.only(right: 40, left: 9),
                      width: 145,
                      height: WalleTTicketStyle.formFieldHeight,
                      decoration: const BoxDecoration(
                        color: Colors.white,
                      ),
                      child: TextFormField(
                        textAlignVertical: TextAlignVertical.bottom,
                        controller: emailController,
                        onSaved: (String? value) {
                          // This optional block of code can be used to run
                          // code when the user saves the form.
                        },
                        decoration: InputDecoration(
                            isDense: true,
                            contentPadding: EdgeInsets.all(10),
                            errorBorder: WalleTTicketStyle.formBorder,
                            focusedErrorBorder: WalleTTicketStyle.formBorder,
                            enabledBorder: WalleTTicketStyle.formBorder,
                            focusedBorder: WalleTTicketStyle.formBorder,
                            fillColor: Colors.black,
                            hintText: "Garantía",
                            hintStyle: const TextStyle(color: Colors.grey)),
                      ),
                    ),
                  ],
                ),
              ),
              Container(
                margin: const EdgeInsets.only(top: 20),
                child: Row(
                  children: [
                    Container(
                      margin: const EdgeInsets.only(left: 37, right: 16),
                      width: 145,
                      height: WalleTTicketStyle.formFieldHeight,
                      decoration: const BoxDecoration(
                        color: Colors.white,
                      ),
                      child: TextFormField(
                        textAlignVertical: TextAlignVertical.bottom,
                        controller: emailController,
                        onSaved: (String? value) {
                          // This optional block of code can be used to run
                          // code when the user saves the form.
                        },
                        decoration: InputDecoration(
                            isDense: true,
                            contentPadding: EdgeInsets.all(10),
                            errorBorder: WalleTTicketStyle.formBorder,
                            focusedErrorBorder: WalleTTicketStyle.formBorder,
                            enabledBorder: WalleTTicketStyle.formBorder,
                            focusedBorder: WalleTTicketStyle.formBorder,
                            fillColor: Colors.black,
                            hintText: "Categoría",
                            hintStyle: const TextStyle(color: Colors.grey)),
                      ),
                    ),
                    Container(
                      margin: const EdgeInsets.only(right: 40, left: 9),
                      width: 145,
                      height: WalleTTicketStyle.formFieldHeight,
                      decoration: const BoxDecoration(
                        color: Colors.white,
                      ),
                      child: TextFormField(
                        textAlignVertical: TextAlignVertical.bottom,
                        controller: emailController,
                        onSaved: (String? value) {
                          // This optional block of code can be used to run
                          // code when the user saves the form.
                        },
                        decoration: InputDecoration(
                            isDense: true,
                            contentPadding: EdgeInsets.all(10),
                            errorBorder: WalleTTicketStyle.formBorder,
                            focusedErrorBorder: WalleTTicketStyle.formBorder,
                            enabledBorder: WalleTTicketStyle.formBorder,
                            focusedBorder: WalleTTicketStyle.formBorder,
                            fillColor: Colors.black,
                            hintText: "Compañía",
                            hintStyle: const TextStyle(color: Colors.grey)),
                      ),
                    ),
                  ],
                ),
              ),
              Container(
                margin: const EdgeInsets.only(top: 20),
                child: Row(
                  children: [
                    Container(
                      margin: const EdgeInsets.only(left: 37, right: 16),
                      width: 145,
                      height: WalleTTicketStyle.formFieldHeight,
                      decoration: const BoxDecoration(
                        color: Colors.white,
                      ),
                      child: TextFormField(
                        textAlignVertical: TextAlignVertical.bottom,
                        controller: emailController,
                        onSaved: (String? value) {
                          // This optional block of code can be used to run
                          // code when the user saves the form.
                        },
                        decoration: InputDecoration(
                            isDense: true,
                            contentPadding: EdgeInsets.all(10),
                            errorBorder: WalleTTicketStyle.formBorder,
                            focusedErrorBorder: WalleTTicketStyle.formBorder,
                            enabledBorder: WalleTTicketStyle.formBorder,
                            focusedBorder: WalleTTicketStyle.formBorder,
                            fillColor: Colors.black,
                            hintText: "Precio",
                            hintStyle: const TextStyle(color: Colors.grey)),
                      ),
                    ),
                  ],
                ),
              )
            ],
          ),
        ),
      ),
    ));
  }
}
