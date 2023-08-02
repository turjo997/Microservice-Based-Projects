import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:training_management_system/landing_screen_trainee/landing_screen_trainee.dart';
import 'package:training_management_system/provider/batches_of_trainer_provider.dart';
import 'package:training_management_system/provider/add_trainee_to_batch_provider.dart';
import 'package:training_management_system/provider/add_trainer_to_batch_provider.dart';
import 'package:training_management_system/provider/admin_batch_info_provider.dart';
import 'package:training_management_system/provider/admin_dashboard_provider.dart';
import 'package:training_management_system/provider/assignment_trainer_provider.dart';
import 'package:training_management_system/provider/batch_details_provider.dart';
import 'package:training_management_system/provider/batch_info_trainee_provider.dart';
import 'package:training_management_system/provider/batch_info_trainer_provider.dart';
import 'package:training_management_system/provider/classroom_provider.dart';
import 'package:training_management_system/provider/course_info_provider.dart';
import 'package:training_management_system/provider/course_info_trainer_trainee_provider.dart';
import 'package:training_management_system/provider/create_user_provider.dart';
import 'package:training_management_system/provider/login_provider.dart';
import 'package:training_management_system/provider/menu_provider.dart';
import 'package:training_management_system/provider/notice_provider.dart';
import 'package:training_management_system/provider/schedule_provider.dart';
import 'package:training_management_system/provider/submission_list_provider.dart';
import 'package:training_management_system/provider/trainee_dashboard_provider.dart';
import 'package:training_management_system/provider/trainee_menu_provider.dart';
import 'package:training_management_system/provider/trainee_profile_provider.dart';
import 'package:training_management_system/provider/trainer_dashboard_provider.dart';
import 'package:training_management_system/provider/trainer_menu_provider.dart';
import 'package:training_management_system/provider/trainer_profile_provider.dart';
import 'package:training_management_system/screen/batch/add_trainee_to_batch_old.dart';
import 'package:training_management_system/screen/batch/add_trainer_to_batch_old.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:training_management_system/screen/log_reg/login.dart';
import 'package:training_management_system/trainer_screen/submission_list.dart';
import 'landing_screen/landing_screen.dart';
import 'landing_screen_trainer/landing_screen_trainer.dart';


void main() {
  runApp(
    MultiProvider(
    providers: [
      ChangeNotifierProvider(create: (_) => AdminDashboardProvider()),
      ChangeNotifierProvider(create: (_) => LoginProvider()),
      ChangeNotifierProvider(create: (_) => CreateUserProvider()),
      ChangeNotifierProvider(create: (_) => TrainerDashboardProvider()),
      ChangeNotifierProvider(create: (_) => TraineeDashboardProvider()),
      ChangeNotifierProvider(create: (_) => AdminBatchInfoProvider()),
      ChangeNotifierProvider(create: (_) => AddTrainerToBatchProvider()),
      ChangeNotifierProvider(create: (_) => AddTraineeToBatchProvider()),
      ChangeNotifierProvider(create: (_) => BatchDetailsProvider()),
      ChangeNotifierProvider(create: (_) => CourseInfoProvider()),
      ChangeNotifierProvider(create: (_) => Menuprovider()),
      ChangeNotifierProvider(create: (_) => TrainerMenuProvider()),
      ChangeNotifierProvider(create: (_) => BatchesOfTrainerProvider()),
      ChangeNotifierProvider(create: (_) => AssignmentTrainerProvider()),
      ChangeNotifierProvider(create: (_) => BatchInfoTrainerProvider()),
      ChangeNotifierProvider(create: (_) => CourseInfoTrainerTraineeProvider()),
      ChangeNotifierProvider(create: (_) => ScheduleProvider()),
      ChangeNotifierProvider(create: (_) => ClassRoomProvider()),
      ChangeNotifierProvider(create: (_) => SubmissionListProvider()),
      ChangeNotifierProvider(create: (_) => TraineeMenuProvider()),
      ChangeNotifierProvider(create: (_) => BatchInfoTraineeProvider()),
      ChangeNotifierProvider(create: (_) => NoticeProvider()),
      ChangeNotifierProvider(create: (_) => TrainerProfileProvider()),
      ChangeNotifierProvider(create: (_) => TraineeProfileProvider()),




    ],
      child :MyApp(),
  ),
  );
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Flutter Demo',
      theme: ThemeData(
        textTheme: GoogleFonts.montserratTextTheme(),
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      initialRoute: '/',
      routes: {
        '/': (context) => Login(),

        'AdminHome': (context) => LandingScreen(),
        'LandingScreenTrainer': (context) => LandingScreenTrainer(),
        'LandingScreenTrainee': (context) => LandingScreenTrainee(),

        'AddTrainerToBatch' : (context) => AddTrainerToBatchOld(),
        'AddTraineeToBatch' : (context) => AddTraineeToBatchOld(),
        'SubmissionList' : (context) => SubmissionList(),


      },
    );
  }
}

