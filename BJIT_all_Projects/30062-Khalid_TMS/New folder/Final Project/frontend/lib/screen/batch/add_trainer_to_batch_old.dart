import 'package:animated_text_kit/animated_text_kit.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:universal_html/html.dart' as html;
import 'package:training_management_system/components/box_decorations.dart';
import 'package:training_management_system/components/color.dart';
import 'package:training_management_system/components/screen_size.dart';
import 'package:training_management_system/provider/add_trainer_to_batch_provider.dart';

import '../../components/my_app_bar.dart';
import '../../components/text_style.dart';

class AddTrainerToBatchOld extends StatefulWidget {

   AddTrainerToBatchOld({Key? key,}) : super(key: key);

  @override
  State<AddTrainerToBatchOld> createState() => _AddTrainerToBatchOldState();
}

class _AddTrainerToBatchOldState extends State<AddTrainerToBatchOld> {

  @override
  Widget build(BuildContext context) {
    return Consumer<AddTrainerToBatchProvider>(
      builder: (context, addTrainerToBatchProvider, child) {
        return Scaffold(
          appBar: MyAppBar(
            title: 'TMS: Add Trainer to Batch',
          ),
          body: SingleChildScrollView(
            child: Column(
              children: [
                Container(
                  width: width(context) * 1,
                  height: height(context) * 1,
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.start,
                    children: [
                      Expanded(
                        flex: 2,
                        child: Container(
                          child: sideBar(),
                        ),
                      ),
                      //body
                      // Expanded(
                      //   flex: 4,
                      //   child: Center(
                      //     child: AddTrainerToBatch(),
                      //   ),
                      // ),

                      Expanded(
                        flex: 1,
                        child: Container(
                        ),
                      ),
                    ],
                  ),
                ),
              ],
            ),
          ),
        );
      }
    );
  }



}



class sideBar extends StatelessWidget {
   sideBar({
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    return Consumer(
        builder: (context,addTrainerToBatchProvider,child){
          return Padding(
            padding: const EdgeInsets.symmetric(
                horizontal: 38.0),
            child: Container(
              height: height(context) * .80,
              decoration: box12Sidebar,
              //color: Colors.orangeAccent,
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  InkWell(
                    onTap: () {},
                    child: Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: Container(
                        //decoration: box12Sidebar,
                        child: TextLiquidFill(
                          text: 'Profile',
                          waveColor: sweetYellow,
                          boxBackgroundColor: Colors.black,
                          textStyle: black20,
                          boxHeight: 70,
                        ),
                      ),
                    ),
                  ),
                  InkWell(
                    onTap: () {
                      //Navigator.pop(context);
                      Navigator.pushNamed(
                          context, "AdminDashboard");
                    },
                    child: Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: Container(
                        //decoration: box12Sidebar,
                        child: TextLiquidFill(
                          text: 'BATCH',
                          waveColor: sweetYellow,
                          boxBackgroundColor: Colors.black,
                          textStyle: black20,
                          boxHeight: 70,
                        ),
                      ),
                    ),
                  ),
                  InkWell(
                    onTap: () {
                      Navigator.pushNamed(
                          context, "TraineeRegister");
                    },
                    child: Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: Container(
                        child: TextLiquidFill(
                          text: 'CREATE ACCOUNT',
                          waveColor: sweetYellow,
                          boxBackgroundColor: Colors.black,
                          textStyle: black20,
                          boxHeight: 70,
                        ),
                      ),
                    ),
                  ),
                  InkWell(
                    onTap: () {},
                    child: Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: Container(
                        child: TextLiquidFill(
                          text: 'Logout',
                          waveColor: sweetYellow,
                          boxBackgroundColor: Colors.black,
                          textStyle: black20,
                          boxHeight: 70,
                        ),
                      ),
                    ),
                  ),

                ],
              ),
            ),
          );
        }
    );
  }
}



