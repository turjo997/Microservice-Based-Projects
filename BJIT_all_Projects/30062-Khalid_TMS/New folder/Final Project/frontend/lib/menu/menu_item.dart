import 'package:animated_text_kit/animated_text_kit.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:training_management_system/provider/menu_provider.dart';
import '../components/color.dart';
import '../components/screen_size.dart';
import '../components/text_style.dart';


enum MenuItem {
  createTrainee,
  createTrainer,
  createAdmin,
  BatchList,
  BatchInformation,
  AddTraineeToBatch,
  AddTrainerToBatch,
  BatchDetailsPage,
  CourseInfoPage,

}


class MenuWidget extends StatelessWidget {
  final void Function(MenuItem) onMenuItemSelected;

  MenuWidget({required this.onMenuItemSelected});

  @override
  Widget build(BuildContext context) {
    return Consumer<Menuprovider>(builder: (context, menuProvider, child){
      return Container(
        color: primary,

        child:Padding(
          padding: const EdgeInsets.symmetric(horizontal: 38.0),
          child: Container(
            height: height(context) ,
            child: SingleChildScrollView(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Container(
                    margin: EdgeInsets.only(bottom: 80),
                    child: Text('TMS',
                      style: TextStyle(
                        fontSize: 50,
                        fontWeight: FontWeight.w900,
                        color: Colors.white,
                        letterSpacing: 5,
                      ),),
                  ),

                  InkWell(
                    onTap: (){
                      menuProvider.onClickSideMenu(2);
                      onMenuItemSelected(MenuItem.BatchList);
                    },
                    child: Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: Container(
                        child: TextLiquidFill(
                          text: 'Batch',
                          waveColor: menuProvider.batch? Colors.white : sweetYellow,
                          boxBackgroundColor: menuProvider.batch? secondary : Colors.black ,
                          textStyle: black20,
                          boxHeight: 50,
                        ),
                      ),
                    ),
                  ),
                  InkWell(
                    onTap: (){
                      menuProvider.onClickSideMenu(3);
                      onMenuItemSelected(MenuItem.createAdmin);
                    },
                    child: Padding(padding: const EdgeInsets.all(8.0),
                      child: Container(
                        child: TextLiquidFill(
                          text: 'Create Admin',
                          waveColor: menuProvider.createAD? Colors.white : sweetYellow,
                          boxBackgroundColor: menuProvider.createAD? secondary : Colors.black ,
                          textStyle: black20,
                          boxHeight: 50,
                        ),
                      ),
                    ),
                  ),
                  InkWell(
                    onTap: (){
                      menuProvider.onClickSideMenu(4);
                      onMenuItemSelected(MenuItem.createTrainee);
                    },
                    child: Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: Container(
                        child: TextLiquidFill(
                          text: 'Create Trainee',
                          waveColor: menuProvider.createTE? Colors.white : sweetYellow,
                          boxBackgroundColor: menuProvider.createTE? secondary : Colors.black ,
                          textStyle: black20,
                          boxHeight: 50,
                        ),
                      ),
                    ),
                  ),
                  InkWell(
                    onTap: (){
                      menuProvider.onClickSideMenu(5);
                      onMenuItemSelected(MenuItem.createTrainer);
                    },
                    child: Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: Container(
                        child: TextLiquidFill(
                          text: 'Create Trainer',
                          waveColor: menuProvider.createTR? Colors.white : sweetYellow,
                          boxBackgroundColor: menuProvider.createTR? secondary : Colors.black ,
                          textStyle: black20,
                          boxHeight: 50,
                        ),
                      ),
                    ),
                  ),
                  InkWell(
                    onTap: (){
                      menuProvider.onClickSideMenu(1);
                      menuProvider.saveRoleToLocalStorage("");
                      menuProvider.saveTokenToLocalStorage("");
                      Navigator.pushReplacementNamed(context, "/");
                    },
                    child: Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: Container(
                        child: TextLiquidFill(
                          text: 'Logout',
                          waveColor: menuProvider.logout? Colors.white : sweetYellow,
                          boxBackgroundColor: menuProvider.logout? secondary : Colors.black ,
                          textStyle: black20,
                          boxHeight: 50,
                          waveDuration: Duration(seconds: 90),
                        ),
                      ),
                    ),
                  ),

                ],
              ),
            ),
          ),
        ),
      );
    });
  }
}

