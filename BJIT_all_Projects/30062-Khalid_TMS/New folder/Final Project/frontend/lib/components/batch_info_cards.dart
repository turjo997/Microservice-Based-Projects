import 'package:flutter/material.dart';
import 'package:training_management_system/components/screen_size.dart';

import 'box_decorations.dart';


class CardsOfBatchInfo {

  Widget cards(BuildContext context, String title, String subtitle) {
    return Padding(
      padding: const EdgeInsets.all(8.0),
      child: SizedBox(
        width: width(context) * 0.16,
        height: height(context) * 0.25,
        child: Card(
          elevation: 4,
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(10),
          ),
          child: Container(
            padding: EdgeInsets.all(16),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                Text(
                  title,
                  style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
                ),
                SizedBox(height: 10),
                Text(
                  subtitle,
                  style: TextStyle(fontSize: 14),
                ),
                SizedBox(height: 15),
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Text(
                      'Go to $title',
                      style: TextStyle(fontSize: 10),
                    ),
                    Icon(Icons.arrow_forward, size: 14),
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

