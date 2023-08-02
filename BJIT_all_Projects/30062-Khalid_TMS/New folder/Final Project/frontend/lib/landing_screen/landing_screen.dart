import 'package:flutter/material.dart';
import 'package:training_management_system/components/batch_info_cards.dart';
import 'package:training_management_system/landing_screen/right_widget.dart';
import 'package:training_management_system/screen/batch/batchlist.dart';
import 'package:training_management_system/screen/create_user_tms/create_trainer_form.dart';

import '../components/text_style.dart';
import '../menu/menu_item.dart';
import '../screen/batch/add_trainee_to_batch.dart';
import '../screen/batch/add_trainer_to_batch.dart';
import '../screen/batch/batch_details.dart';
import '../screen/batch/batch_information.dart';
import '../screen/batch/course_info_of_batch.dart';
import '../screen/create_user_tms/create_admin_form.dart';
import '../screen/create_user_tms/create_trainee_form.dart';




class LandingScreen extends StatefulWidget {
  @override
  State<LandingScreen> createState() => _LandingScreenState();
}

class _LandingScreenState extends State<LandingScreen> {
  @override

  bool flag = false;
  final _middleContentWidgetKey = GlobalKey<_MiddleContentWidgetState>();



  void onMenuItemSelected(MenuItem menuItem) {

    switch (menuItem) {

      case MenuItem.CourseInfoPage:
        _middleContentWidgetKey.currentState?.changeContent(CourseInfoPage());
        break;

      case MenuItem.BatchDetailsPage:
        _middleContentWidgetKey.currentState?.changeContent(BatchDetailsPage());
        break;

      case MenuItem.AddTrainerToBatch:
      _middleContentWidgetKey.currentState?.changeContent(AddTrainerToBatch());
      break;

      case MenuItem.AddTraineeToBatch:
        _middleContentWidgetKey.currentState?.changeContent(AddTraineeToBatch());
        break;

      case MenuItem.BatchInformation:
        CardsOfBatchInfo cardsOfBatchInfo = CardsOfBatchInfo();
        _middleContentWidgetKey.currentState?.changeContent(BatchInformation(onMenuItemSelected: onMenuItemSelected, cardsOfBatchInfo: cardsOfBatchInfo,));
        break;

      case MenuItem.BatchList:
        _middleContentWidgetKey.currentState?.changeContent(BatchList(onMenuItemSelected: onMenuItemSelected,));
        break;

      case MenuItem.createAdmin:
        _middleContentWidgetKey.currentState?.changeContent(CreateAdminForm());
        break;

      case MenuItem.createTrainee:
        _middleContentWidgetKey.currentState?.changeContent(CreateTraineeForm());
        break;

      case MenuItem.createTrainer:
        _middleContentWidgetKey.currentState?.changeContent(CreateTrainerForm());
        break;


    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(

      floatingActionButton: flag ?Container(
      height: 100,
      width: 200,

      child: FloatingActionButton(

        backgroundColor: Colors.black,
        child: Text("ADD BATCH",style: sweet20,),
        onPressed: () {

        },

      ),
    )
      : Container(),


     //appBar: AppBar(title: const Text('Dynamic Content Switching')),
      body: Row(
        children: [
          // Menu section
          Expanded(
            flex: 1,
            child: MenuWidget(onMenuItemSelected: onMenuItemSelected),
          ),
          // Middle content section
          Expanded(
            flex: 3,
            child: MiddleContentWidget(key: _middleContentWidgetKey, onMenuItemSelected: onMenuItemSelected,),
          ),
          // Last section
          Expanded(
            flex: 1,
            child: LastContentWidget(),
          ),
        ],
      ),
    );
  }
}


///   DYNAMIC MID SECTION


class MiddleContentWidget extends StatefulWidget {
  final void Function(MenuItem) onMenuItemSelected;

  MiddleContentWidget({
    Key? key,
    required this.onMenuItemSelected,
  }) : super(key: key);

  @override
  _MiddleContentWidgetState createState() => _MiddleContentWidgetState();
}

class _MiddleContentWidgetState extends State<MiddleContentWidget> {
  @override
  void initState() {
    CardsOfBatchInfo cardsOfBatchInfo = CardsOfBatchInfo();
    changeContent(BatchList(onMenuItemSelected: widget.onMenuItemSelected,));
    super.initState();
  }

  Widget _currentContent = Container(
    child:  Center(
      child: SingleChildScrollView(
        child: Column(
          children: [
            // Container(
            //   child: Text(""),
            //   height: 50,
            //   width: double.infinity,
            //   color: sweetYellow,
            // ),
            Text("I LOVE KHALISI"),
            SizedBox(height: 500,),
            Text("I LOVE KHALISI"),
            SizedBox(height: 500,),
          ],
        ),
      ),
    ),
  ); // Initial content (can be any widget)

  void changeContent(Widget newContent) {
    setState(() {
      _currentContent = newContent;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Center(
      child: _currentContent,
    );
  }
}

