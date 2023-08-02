import 'package:animated_text_kit/animated_text_kit.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../components/color.dart';
import '../components/screen_size.dart';
import '../components/text_style.dart';
import '../provider/trainee_menu_provider.dart';

enum TraineeMenuItem {
  profile,
  batch,
  course,
  batchDetailsPage,
  schedule,
  assignmentTrainee,
  notice,
  classroom
}

class TraineeMenuWidget extends StatelessWidget {
  final void Function(TraineeMenuItem) onMenuItemSelected;

  TraineeMenuWidget({required this.onMenuItemSelected});

  @override
  Widget build(BuildContext context) {
    return Consumer<TraineeMenuProvider>(builder: (context, traineeMenuProvider, child){
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
                      traineeMenuProvider.onClickSideMenu(1);
                      onMenuItemSelected(TraineeMenuItem.profile);
                    },
                    child: Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: Container(
                        //decoration: box12Sidebar,
                        child: TextLiquidFill(
                          text: 'Profile',
                          waveColor: traineeMenuProvider.profile? Colors.white : sweetYellow,
                          boxBackgroundColor: traineeMenuProvider.profile? secondary : Colors.black ,
                          textStyle: black20,
                          boxHeight: 50,
                        ),
                      ),
                    ),
                  ),

                  InkWell(
                    onTap: (){
                      traineeMenuProvider.onClickSideMenu(2);
                      traineeMenuProvider.getTraineeIdByUserId();
                      onMenuItemSelected(TraineeMenuItem.batch);

                    },
                    child: Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: Container(
                        child: TextLiquidFill(
                          text: 'Batch',
                          waveColor: traineeMenuProvider.batch? Colors.white : sweetYellow,
                          boxBackgroundColor: traineeMenuProvider.batch? secondary : Colors.black ,
                          textStyle: black20,
                          boxHeight: 50,
                        ),
                      ),
                    ),
                  ),

                  InkWell(
                    onTap: (){
                      traineeMenuProvider.onClickSideMenu(1);
                      traineeMenuProvider.saveRoleToLocalStorage("");
                      traineeMenuProvider.saveTokenToLocalStorage("");
                      traineeMenuProvider.saveBatchIdInLocalStorage("");
                      traineeMenuProvider.saveTraineeIdInLocalStorage("");
                      Navigator.pushReplacementNamed(context, "/");
                    },
                    child: Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: Container(
                        child: TextLiquidFill(
                          text: 'Logout',
                          waveColor: traineeMenuProvider.logout? Colors.white : sweetYellow,
                          boxBackgroundColor: traineeMenuProvider.logout? secondary : Colors.black ,
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