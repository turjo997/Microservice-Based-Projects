import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:universal_html/html.dart' as html;
import 'package:flutter/material.dart';



class NoticeProvider with ChangeNotifier{


  Future<List<dynamic>> getAllNotice() async {
    String? batchId = getBatchIdFromLocalStorage();
    String? token = getTokenFromLocalStorage();

    if (token != null) {
    } else {

    }
    String url = "http://localhost:8090/classroom/notice-board/$batchId";
    final response = await http.get(
      Uri.parse(url),
      headers: {"Authorization": "Bearer $token"},
    );

    if (response.statusCode == 200) {
      var data = jsonDecode(response.body);
      data = data["notices"];
      int c = data.length-1;
      var temp = [];

      for(int i= c; i>=0; i--){
        temp.add(data[i]);
      }

      return temp;
    }
    return [];
  }

  Future<void> createNotice(var noticeInfo, BuildContext context) async {

    String? token = getTokenFromLocalStorage();
    String? url = 'http://localhost:8090/classroom/notice';

    final headers = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer $token',
    };


    String? noticeBoardId =  getBatchIdFromLocalStorage();
    String? trainerId = getTrainerIdFromLocalStorage();
    print("-----------> trainer id  $trainerId");

    final data = {
      "noticeTitle": noticeInfo['noticeTitle'],
      "textData": noticeInfo['textData'],
      "postDate": DateTime.now().toString(),
      "noticeBoardId": noticeBoardId,
      "trainerId": trainerId,

    };
    final jsonBody = jsonEncode(data);
    final response =
    await http.post(Uri.parse(url), headers: headers, body: jsonBody);

    if (response.statusCode == 200) {
      print('Data posted successfully');
      notifyListeners();
      Navigator.pop(context);
    } else {

      print('Error occurred while posting data: ${response.body}');
    }
  }

  String? getBatchIdFromLocalStorage() {
    final storage = html.window.localStorage;
    return storage['batchId'];
  }

  String? getTokenFromLocalStorage() {
    final storage = html.window.localStorage;
    return storage['token'];
  }

  String? getTrainerIdFromLocalStorage() {
    final storage = html.window.localStorage;
    return storage['trainerId'];
  }

  String? getRoleFromLocalStorage() {
    final storage = html.window.localStorage;
    return storage['role'];

  }


}