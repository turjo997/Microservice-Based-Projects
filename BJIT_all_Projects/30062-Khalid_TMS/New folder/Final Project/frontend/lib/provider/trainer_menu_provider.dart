import 'package:flutter/material.dart';
import 'package:universal_html/html.dart' as html;

class TrainerMenuProvider with ChangeNotifier{
  bool profile = false;
  bool batch = true;
  bool logout = false;

  void onClickSideMenu(int c){
    profile = false;
    batch = false;
    logout = false;
    if(c==1) profile = true;
    if(c==2) batch = true;
    if(c==3) logout = true;
    notifyListeners();

  }



  void saveTokenToLocalStorage(String token) {
    final storage = html.window.localStorage;
    storage['token'] = token;
    notifyListeners();
  }

  String? getTokenFromLocalStorage() {
    final storage = html.window.localStorage;
    return storage['token'];
  }

  void saveRoleToLocalStorage(String role) {
    final storage = html.window.localStorage;
    storage['role'] = role;
    notifyListeners();
  }

  void saveBatchIdInLocalStorage(String batchId) {
    final storage = html.window.localStorage;
    storage['batchId'] = batchId;
  }

  void saveTrainerIdInLocalStorage(String trainerId) {
    final storage = html.window.localStorage;
    storage['trainerId'] = trainerId;
  }

  String? getRoleFromLocalStorage() {
    final storage = html.window.localStorage;
    return storage['role'];
  }
}