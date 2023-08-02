import 'package:flutter/material.dart';
import 'package:quickalert/quickalert.dart';
import 'package:training_management_system/components/color.dart';


class Noti{

 Future noti(BuildContext context, QuickAlertType type, String message){

   return QuickAlert.show(
       context: context,
       type: type,
       text: message,
       width: 40,
       confirmBtnColor: Colors.black,
       confirmBtnTextStyle: TextStyle(color: sweetYellow),
      autoCloseDuration: Duration(seconds: 1),

   );

 }

}