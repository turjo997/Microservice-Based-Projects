import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../provider/trainee_profile_provider.dart';

class TraineeProfile extends StatefulWidget {
  const TraineeProfile({Key? key}) : super(key: key);

  @override
  _TraineeProfileState createState() => _TraineeProfileState();
}

class _TraineeProfileState extends State<TraineeProfile> {
  @override
  Widget build(BuildContext context) {
    return Consumer<TraineeProfileProvider>(
      builder: (context, traineeProfileProvider, child) {
        return Container(
          padding: EdgeInsets.all(16),
          child: FutureBuilder<dynamic>(
            future: traineeProfileProvider.getUserInfobyUserId(),
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
                            padding: EdgeInsets.only(left: 40, bottom: 20),
                            child: ClipOval(
                              child: Image.network(
                                "http://localhost:8090/trainee/profile-picture/" +
                                    userInfo["traineeId"].toString(),
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
                      padding: EdgeInsets.only(left: 40),
                      child: Text(
                        userInfo["fullName"],
                        style: TextStyle(
                          fontSize: 24,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                    ),
                    SizedBox(height: 16),
                    Row(
                      children: [
                        Expanded(
                          child: Padding(
                            padding: EdgeInsets.only(left: 40),
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                buildProfileInfo("Gender", userInfo["gender"]),
                                buildProfileInfo("Date of Birth", userInfo["dateOfBirth"]),
                                buildProfileInfo("Email", userInfo["email"]),
                                buildProfileInfo("Contact Number", userInfo["contactNumber"]),
                              ],
                            ),
                          ),
                        ),
                        Expanded(
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              buildProfileInfo("Degree Name", userInfo["degreeName"]),
                              buildProfileInfo("Educational Institute", userInfo["educationalInstitute"]),
                              buildProfileInfo("CGPA", userInfo["cgpa"]),
                              buildProfileInfo("Passing Year", userInfo["passingYear"]),
                              buildProfileInfo("Present Address", userInfo["presentAddress"]),
                            ],
                          ),
                        ),
                      ],
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



