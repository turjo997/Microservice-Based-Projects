import 'dart:convert';
//import 'package:quickalert/widgets/quickalert_dialog.dart';
import 'package:quickalert/models/quickalert_type.dart';
import 'package:quickalert/widgets/quickalert_dialog.dart';
import 'package:universal_html/html.dart' as html;
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:jwt_decoder/jwt_decoder.dart';
import 'package:shared_preferences/shared_preferences.dart';


import '../components/quickAlert.dart';
class LoginProvider with ChangeNotifier {
  String role = "";
  int userId = -1;
  int statusCode = 500;

  Future<void> getRole(String email, String password, BuildContext context) async {
    const url1 = "http://localhost:8090/auth/login";
    final url = Uri.parse(url1);
    final headers = {
      "Access-Control-Allow-Origin": "*",
      'Content-Type': 'application/json',
      'Accept': '*/*'
    };
    final body = json.encode({"email": email, "password": password});

    try {
      final response = await http.post(url, headers: headers, body: body);
      if (response.statusCode == 200) {
        statusCode = 200;
        var responseBody = jsonDecode(response.body);
        String token = responseBody["token"];
        saveTokenToLocalStorage(token);
        Map<String, dynamic> decodedToken = JwtDecoder.decode(token);
        if (decodedToken != null) {
          role = decodedToken['role'][0];
          try {
            userId = decodedToken["userId"];
            saveUserIdToLocalStorage(userId.toString());

          }catch(e){
            Noti notification = new Noti();
            notification.noti(context, QuickAlertType.error, "Login Failed");

          }
          saveRoleToLocalStorage(role);

          Noti notification = new Noti();
          notification.noti(context, QuickAlertType.success, "Login Successfull");
        } else {
          Noti notification = new Noti();
          notification.noti(context, QuickAlertType.error, "Login Failed, Check Credentials");
        }

      } else {
        statusCode = 500;
        print('Failed to create post. Error code: ${response.statusCode}');
      }
    } catch (e) {
      print('Error sending POST request: $e');
    }
    notifyListeners();
  }

  void saveTokenToLocalStorage(String token) {
    final storage = html.window.localStorage;
    storage['token'] = token;
    notifyListeners();
  }

  String? getTokenFromLocalstorage() {
    final storage = html.window.localStorage;
    return storage['token'];
  }

  void saveRoleToLocalStorage(String role) {
    final storage = html.window.localStorage;
    storage['role'] = role;
    print(role);
    notifyListeners();
  }

  String? getRoleFromLocalStorage() {
    final storage = html.window.localStorage;
    return storage['role'];

  }
  void saveUserIdToLocalStorage(String userId) {
    final storage = html.window.localStorage;
    storage['userId'] = userId;
    print(userId);
    notifyListeners();
  }

  String? getUserIdFromLocalStorage() {
    final storage = html.window.localStorage;
    return storage['userId'];
  }

  void showAutoPopAlert(BuildContext context) {
    QuickAlert.show(
      autoCloseDuration: Duration(seconds: 1),
        context: context,
        type: QuickAlertType.success,
      width: 40,
      text: 'Transaction Completed Successfully!',
    );

  }
}