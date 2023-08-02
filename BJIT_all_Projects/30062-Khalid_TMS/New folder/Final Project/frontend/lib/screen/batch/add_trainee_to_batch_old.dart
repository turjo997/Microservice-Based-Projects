import 'package:animated_text_kit/animated_text_kit.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:training_management_system/provider/add_trainee_to_batch_provider.dart';
import 'package:universal_html/html.dart' as html;

import '../../components/box_decorations.dart';
import '../../components/color.dart';
import '../../components/my_app_bar.dart';
import '../../components/screen_size.dart';
import '../../components/text_style.dart';
import '../../provider/add_trainer_to_batch_provider.dart';

class AddTraineeToBatchOld extends StatefulWidget {

  AddTraineeToBatchOld({Key? key,}) : super(key: key);

  @override
  State<AddTraineeToBatchOld> createState() => _AddTraineeToBatchOldState();
}

class _AddTraineeToBatchOldState extends State<AddTraineeToBatchOld> {

  @override
  Widget build(BuildContext context) {
    return Consumer<AddTraineeToBatchProvider>(
        builder: (context, addTraineeToBatchProvider, child) {
          return Scaffold(
            appBar: MyAppBar(
              title: 'TMS: Add Trainee to Batch',
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
                            child: Padding(
                              padding: const EdgeInsets.symmetric(
                                  horizontal: 38.0),
                              child: Container(
                                height: height(context) * .80,
                                decoration: box12Sidebar,
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
                            ),
                          ),
                        ),
                        //body
                        // Expanded(
                        //   flex: 4,
                        //   child: Center(
                        //     child: AddTraineeToBatch(),
                        //   ),
                        // ),
                        //
                        // Expanded(
                        //   flex: 1,
                        //   child: Container(
                        //   ),
                        // ),
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


  String? getBatchIdInLocalStorage() {
    final storage = html.window.localStorage;
    return storage['batchId'];
  }
}


