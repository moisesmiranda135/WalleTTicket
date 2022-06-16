import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';

import 'package:http/http.dart' as http;
import 'package:walletticket_app/models/ticket/ticket_response.dart';
import 'package:walletticket_app/styles/styles.dart';
import 'package:walletticket_app/ui/screens/home_screen.dart';

class DetailTicketScreen extends StatelessWidget {
  final TicketResponse ticket;

  const DetailTicketScreen({Key? key, required this.ticket}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SingleChildScrollView(
        child: Container(
          color: Colors.grey[200],
          child: Center(
            child: Column(
              children: [
                Column(
                  children: [
                    Container(
                      margin: const EdgeInsets.only(top: 20, left: 30),
                      child: Row(
                        children: [
                          Text(
                            ticket.categoryName,
                            style: TextStyle(
                                fontSize: 24,
                                fontWeight: FontWeight.w800,
                                color: Colors.black),
                          ),
                          Container(
                            width: 80,
                          ),
                          Column(
                            children: [
                              IconButton(
                                icon: Icon(Icons.post_add_outlined),
                                iconSize: 50,
                                onPressed: () {
                                  Navigator.push(
                                      context,
                                      MaterialPageRoute(
                                        builder: (context) => HomeScreen(),
                                      ));
                                },
                              ),
                              Text(
                                "Nuevo Ticket",
                                style: TextStyle(
                                    fontSize: 12,
                                    fontWeight: FontWeight.w800,
                                    color: Colors.black),
                              ),
                            ],
                          ),
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
