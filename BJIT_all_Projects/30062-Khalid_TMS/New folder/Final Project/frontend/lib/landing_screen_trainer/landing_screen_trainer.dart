import 'package:flutter/material.dart';

import 'package:training_management_system/landing_screen/right_widget.dart';
import 'package:training_management_system/trainer_screen/submission_list.dart';
import 'package:training_management_system/trainer_screen/trainer_profile.dart';
import '../classroom/classroom.dart';
import '../menu/trainer_menu_item.dart';
import '../notice/notice.dart';
import '../screen/batch/batch_details.dart';
import '../trainer_screen/assignment_trainer.dart';
import '../trainer_screen/batch_information_trainer.dart';
import '../trainer_screen/batches_of_trainer.dart';
import '../trainer_screen/course_info_trainer.dart';
import '../trainer_screen/schedule_trainer.dart';


// Enum to represent the menu items

class LandingScreenTrainer extends StatefulWidget {
  @override
  State<LandingScreenTrainer> createState() => _LandingScreenTrainerState();
}

class _LandingScreenTrainerState extends State<LandingScreenTrainer> {

  final _middleContentWidgetKey = GlobalKey<_MiddleContentWidgetState>();


  // Function to handle menu item selection
  void onTrainerMenuItemSelected(TrainerMenuItem menuItem) {

    switch (menuItem) {
      case TrainerMenuItem.Notice:
        _middleContentWidgetKey.currentState?.changeContent(Notice());
        break;

      case TrainerMenuItem.submissionList:
        _middleContentWidgetKey.currentState?.changeContent(SubmissionList());
        break;

    case TrainerMenuItem.classroom:
    _middleContentWidgetKey.currentState?.changeContent(ClassroomPage());
    break;

    case TrainerMenuItem.ScheduleTrainer:
    _middleContentWidgetKey.currentState?.changeContent(ScheduleTrainer());
    break;

    case TrainerMenuItem.BatchDetailsPage:
    _middleContentWidgetKey.currentState?.changeContent(BatchDetailsPage());
    break;

      case TrainerMenuItem.CourseInfoTrainer:
        _middleContentWidgetKey.currentState?.changeContent(CourseInfoTrainer());
        break;
      case TrainerMenuItem.AssignmentTrainer:
        _middleContentWidgetKey.currentState?.changeContent(AssignmentTrainer(onMenuItemSelected: onTrainerMenuItemSelected,));
        break;

      case TrainerMenuItem.batchInfo:
        _middleContentWidgetKey.currentState?.changeContent(BatchInformationTrainer(onMenuItemSelected: onTrainerMenuItemSelected,));
        break;

      case TrainerMenuItem.batch:
        _middleContentWidgetKey.currentState?.changeContent(BatchesOfTrainer(onMenuItemSelected: onTrainerMenuItemSelected,));
        break;

      case TrainerMenuItem.profile:
        _middleContentWidgetKey.currentState?.changeContent(TrainerProfile());
        break;


    // Add cases for other menu items
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(

      //appBar: AppBar(title: const Text('Dynamic Content Switching')),
      body: Row(
        children: [
          // Menu section
          Expanded(
            flex: 1,
            child: TrainerMenuWidget(onMenuItemSelected: onTrainerMenuItemSelected),
          ),
          // Middle content section
          Expanded(
            flex: 3,
            child: MiddleContentWidget(key: _middleContentWidgetKey, onMenuItemSelected: onTrainerMenuItemSelected, ),
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
  final void Function(TrainerMenuItem) onMenuItemSelected;
  MiddleContentWidget({Key? key,
    required this.onMenuItemSelected,
  }) : super(key: key);

  @override
  _MiddleContentWidgetState createState() => _MiddleContentWidgetState();
}

class _MiddleContentWidgetState extends State<MiddleContentWidget> {
  @override
  void initState() {

    changeContent(BatchInformationTrainer(onMenuItemSelected: widget.onMenuItemSelected,));
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
            Text("I LOVE yoyoyoyooy         KHALISI"),
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




