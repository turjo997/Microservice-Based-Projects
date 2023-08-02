import 'dart:ui';
import 'package:flutter/material.dart';
import 'package:training_management_system/components/color.dart';

class MyAppBar extends StatelessWidget implements PreferredSizeWidget {
  final String title;
  //final Color backgroundColor;
  final Color textColor = Color(0xFF090A0A);

  MyAppBar({
    required this.title,
   // this.backgroundColor = Colors.red,

  });

  @override
  Size get preferredSize => Size.fromHeight(kToolbarHeight);

  @override
  Widget build(BuildContext context) {
    return AppBar(
      elevation: .8,
      title: Text(
        title,
        style: TextStyle(color: textColor,
            fontSize: 30),
      ),
      backgroundColor: Colors.white,
    );
  }
}
