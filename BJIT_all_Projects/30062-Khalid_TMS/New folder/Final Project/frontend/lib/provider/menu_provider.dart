import 'package:flutter/material.dart';
import 'package:universal_html/html.dart' as html;

class Menuprovider with ChangeNotifier{
  bool profile = false;
  bool batch = true;
  bool createAD = false;
  bool createTR = false;
  bool createTE = false;
  bool logout = false;

  void onClickSideMenu(int c){
     profile = false;
     batch = false;
     createAD = false;
     createTR = false;
     createTE = false;
     logout = false;
     if(c==1) profile = true;
     if(c==2) batch = true;
     if(c==3) createAD = true;
     if(c==4) createTE = true;
     if(c==5) createTR = true;
     if(c==6) logout = true;
     notifyListeners();
  }
  void saveRoleToLocalStorage(String role) {
    final storage = html.window.localStorage;
    storage['role'] = role;
    print(role);
    notifyListeners();
  }
  void saveTokenToLocalStorage(String token) {
    final storage = html.window.localStorage;
    storage['token'] = token;
    notifyListeners();
  }

}