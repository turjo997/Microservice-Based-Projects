import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../provider/trainer_profile_provider.dart';

class TrainerProfile extends StatefulWidget {
  const TrainerProfile({Key? key}) : super(key: key);

  @override
  State<TrainerProfile> createState() => _TrainerProfileState();
}

class _TrainerProfileState extends State<TrainerProfile> {
  @override
  Widget build(BuildContext context) {
    return Consumer<TrainerProfileProvider>(
      builder: (context, trainerProfileProvider, child) {
        return Container(
          padding: EdgeInsets.all(16),
          child: FutureBuilder<dynamic>(
            future: trainerProfileProvider.getUserInfobyUserId(),
            builder: (context, snapshot) {
              if (snapshot.connectionState == ConnectionState.waiting) {
                return Center(
                  child: CircularProgressIndicator(),
                );
              }

              if (!snapshot.hasData || snapshot.data == null) {
                return Text("Error: Data not available");
              }

              var userInfo = snapshot.data as Map<String, dynamic>;

              return SingleChildScrollView(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Padding(
                      padding: EdgeInsets.only(bottom: 16),
                      child: Stack(
                        alignment: Alignment.bottomRight,
                        children: [
                          Padding(
                            padding: EdgeInsets.only(bottom: 20),
                            child: ClipOval(
                              child: Image.network(
                                "http://localhost:8090/trainer/profile-picture/" + userInfo["trainerId"].toString(),
                                height: 200,
                                width: 200,
                                fit: BoxFit.cover,
                              ),
                            ),
                          ),
                          IconButton(
                            icon: Icon(Icons.edit),
                            onPressed: () {
                              // Add your update profile logic here
                            },
                          ),
                        ],
                      ),
                    ),
                    Padding(
                      padding: EdgeInsets.only(left: 30),
                      child: Text(
                        userInfo["fullName"],
                        style: TextStyle(
                          fontSize: 24,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                    ),
                    SizedBox(height: 16),
                    Center(
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          Expanded(
                            child: Padding(
                              padding: EdgeInsets.only(left: 30),
                              child: Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  buildProfileInfo("Designation", userInfo["designation"]),
                                  buildProfileInfo("Joining Date", userInfo["joiningDate"]),
                                  buildProfileInfo("Years of Experience", userInfo["yearsOfExperience"]),
                                ],
                              ),
                            ),
                          ),
                          SizedBox(width: 30), // Adjust the space between the columns here
                          Expanded(
                            child: Padding(
                              padding: EdgeInsets.only(left: 30),
                              child: Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  buildProfileInfo("Email", userInfo["email"]),
                                  buildProfileInfo("Contact Number", userInfo["contactNumber"]),
                                  buildProfileInfo("Present Address", userInfo["presentAddress"]),
                                ],
                              ),
                            ),
                          ),
                        ],
                      ),
                    ),
                  ],
                ),
              );
            },
          ),
        );
      },
    );
  }

  Widget buildProfileInfo(String label, String value) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          label + ":",
          style: TextStyle(
            fontSize: 16,
            fontWeight: FontWeight.bold,
          ),
        ),
        SizedBox(height: 4),
        Text(
          value,
          style: TextStyle(
            fontSize: 16,
          ),
        ),
        SizedBox(height: 8),
      ],
    );
  }
}






