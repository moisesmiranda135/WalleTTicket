import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';

class WalleTTicketStyle {
  static const Color primaryColor = Color.fromRGBO(0, 133, 255, 1);

  // ButtonColors
  static const Color blueButtonB = Color.fromRGBO(0, 133, 255, 1);

  //FormFiled
  static const double formFieldWidth = 280;
  static const double formFieldHeight = 40;
  static const EdgeInsets contentPadding = EdgeInsets.all(30);

  static OutlineInputBorder formBorder = OutlineInputBorder(
      borderRadius: BorderRadius.circular(50.0),
      borderSide: const BorderSide(
        color: WalleTTicketStyle.blueButtonB,
        width: 2.0,
      ));

  //IconsBottomBar
  static const SizedBox separatedIconsBB = SizedBox(
    width: 50,
  );
  static const double containerIconBBSize = 45;
  static const double iconBBSize = 35;

  //Create Ticket Form
  static const double formCreateFieldWidth = 320;

/*
  // Margins, Paddings, Card properties
  static const double bodyPadding = 20.0;
  static const double cardElevation = 1.0;
  static const double cardBorderRadius = 15;

  // Font sizes, TextStyles
  static const double textSizeSmall = 14.0;
  static const double textSizeMedium = 28.0;
  static const double textSizeTitle = 40.0;
*/
/*
  static TextStyle get textTitle => GoogleFonts.getFont(
        'Nunito',
        color: DamStyle.orangeColor,
        fontWeight: FontWeight.w600,
        fontSize: 40,
      );

  */
}
