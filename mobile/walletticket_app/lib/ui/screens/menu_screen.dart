import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:walletticket_app/styles/styles.dart';
import 'package:walletticket_app/ui/screens/favorite_ticket_screen.dart';
import 'package:walletticket_app/ui/screens/home_screen.dart';
import 'package:walletticket_app/ui/screens/profile_screen.dart';

class MenuScreen extends StatefulWidget {
  const MenuScreen({Key? key}) : super(key: key);

  @override
  _MenuScreenState createState() => _MenuScreenState();
}

class _MenuScreenState extends State<MenuScreen> {
  int _currentIndex = 0;

  @override
  void initState() {
    super.initState();
  }

  @override
  void dispose() {
    super.dispose();
  }

  List<Widget> pages = [
    const HomeScreen(),
    const FavoriteTicketScreen(),
    const ProfileScreen()
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text('WalletTicket'),
        ),
        body: pages[_currentIndex],
        bottomNavigationBar: _buildBottomBar());
  }

  Widget _buildBottomBar() {
    return Container(
        height: 70,
        decoration: const BoxDecoration(
          color: Colors.white,
          borderRadius: BorderRadius.only(
              topRight: Radius.circular(20), topLeft: Radius.circular(20)),
          boxShadow: [
            BoxShadow(color: Colors.black38, spreadRadius: 0, blurRadius: 10),
          ],
        ),
        child: ClipRRect(
            borderRadius: const BorderRadius.only(
              topLeft: Radius.circular(30.0),
              topRight: Radius.circular(30.0),
            ),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                GestureDetector(
                  child: Container(
                      width: WalleTTicketStyle.containerIconBBSize,
                      height: WalleTTicketStyle.containerIconBBSize,
                      decoration: BoxDecoration(
                        color: _currentIndex == 0
                            ? Colors.grey[350]
                            : Colors.white,
                        borderRadius:
                            const BorderRadius.all(Radius.circular(10)),
                      ),
                      child: Icon(
                        Icons.home,
                        size: WalleTTicketStyle.iconBBSize,
                        color: _currentIndex == 0
                            ? WalleTTicketStyle.primaryColor
                            : Colors.black,
                      )),
                  onTap: () {
                    setState(() {
                      _currentIndex = 0;
                    });
                  },
                ),
                WalleTTicketStyle.separatedIconsBB,
                GestureDetector(
                  child: Container(
                      width: WalleTTicketStyle.containerIconBBSize,
                      height: WalleTTicketStyle.containerIconBBSize,
                      decoration: BoxDecoration(
                        color: _currentIndex == 1
                            ? Colors.grey[350]
                            : Colors.white,
                        borderRadius:
                            const BorderRadius.all(Radius.circular(10)),
                      ),
                      child: Icon(
                        Icons.favorite_border,
                        size: WalleTTicketStyle.iconBBSize,
                        color: _currentIndex == 1
                            ? WalleTTicketStyle.primaryColor
                            : Colors.black,
                      )),
                  onTap: () {
                    setState(() {
                      _currentIndex = 1;
                    });
                  },
                ),
                WalleTTicketStyle.separatedIconsBB,
                GestureDetector(
                  child: Container(
                    width: WalleTTicketStyle.containerIconBBSize,
                    height: WalleTTicketStyle.containerIconBBSize,
                    decoration: BoxDecoration(
                      color:
                          _currentIndex == 2 ? Colors.grey[350] : Colors.white,
                      borderRadius: const BorderRadius.all(Radius.circular(10)),
                    ),
                    child: Icon(Icons.perm_identity,
                        size: WalleTTicketStyle.iconBBSize,
                        color: _currentIndex == 2
                            ? WalleTTicketStyle.primaryColor
                            : Colors.black),
                  ),
                  onTap: () {
                    setState(() {
                      _currentIndex = 2;
                    });
                  },
                ),
              ],
            )));
  }
}
