import 'package:animated_text_kit/animated_text_kit.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../../components/color.dart';
import '../../components/screen_size.dart';
import '../../components/text_style.dart';
import '../../provider/create_user_provider.dart';

class CreateTraineeForm extends StatelessWidget {
  CreateTraineeForm({
    super.key,
  });

  final name = TextEditingController();
  final email = TextEditingController();
  final password = TextEditingController();
  final contact = TextEditingController();
  final gender = TextEditingController();
  final dateOfBirth = TextEditingController();
  final cgpa = TextEditingController();
  final educationalInstitute = TextEditingController();
  final passingYear = TextEditingController();
  final degreeName = TextEditingController();
  final presentAddress = TextEditingController();
  final createTraineeFormKey = GlobalKey<FormState>();

  @override
  Widget build(BuildContext context) {
    return Consumer<CreateUserProvider>(
      builder: (context, createUserProvider, child) {
        return Column(crossAxisAlignment: CrossAxisAlignment.center, children: [
          Expanded(
              flex: 1,
              child: Padding(
                padding: const EdgeInsets.all(8.0),
                child: Text("Register Trainee",
                  style: TextStyle(
                    fontSize: 30,
                    fontWeight: FontWeight.w900,
                  ),
                ),
              )
          ),
          Expanded(
            flex: 9,
            child: Form(
              key: createTraineeFormKey,
              child: SingleChildScrollView(
                child: Padding(
                  padding:
                      const EdgeInsets.symmetric(vertical: 100, horizontal: 100),
                  child: Column(
                    children: [
                      Row(
                        children: [
                          Expanded(
                            flex: 3,
                            child: Container(
                              width: width(context) * .5,
                              height: height(context) * .1,
                              child: TextFormField(
                                controller: name,
                                validator: (val) {
                                  if (val!.isEmpty) return "required field";
                                },
                                decoration: InputDecoration(
                                  hintText: "Name",
                                ),
                              ),
                            ),
                          ),
                          SizedBox(width: 10),
                          Expanded(
                            flex: 1,
                            child: ElevatedButton(
                              style: ElevatedButton.styleFrom(
                                backgroundColor: Colors.black,
                              ),
                              onPressed: () {
                                createUserProvider.pickImage(context);
                              },
                              child: createUserProvider.image == null
                                  ? Text(
                                      "Select Image",
                                      style: TextStyle(
                                        color: sweetYellow,
                                      ),
                                    )
                                  : Icon(
                                      Icons.thumb_up_sharp,
                                      color: Colors.green,
                                    ),
                            ),
                          ),
                        ],
                      ),
                      Row(
                        children: [
                          Expanded(
                            flex: 1,
                            child: Container(
                              height: height(context) * .1,
                              child: TextFormField(
                                controller: email,
                                validator: (val) {
                                  if (val!.isEmpty) return "required field";
                                },
                                decoration: InputDecoration(
                                  hintText: "Email",
                                ),
                              ),
                            ),
                          ),
                          SizedBox(width: 10),
                          Expanded(
                            flex: 1,
                            child: Container(
                              height: height(context) * .1,
                              child: TextFormField(
                                controller: password,
                                validator: (val) {
                                  if (val!.isEmpty) return "required field";
                                },
                                decoration: InputDecoration(
                                  hintText: "Password",
                                ),
                              ),
                            ),
                          ),
                        ],
                      ),
                      Row(
                        children: [
                          Expanded(
                            flex: 1,
                            child: Container(
                              height: height(context) * .1,
                              child: TextFormField(
                                controller: contact,
                                validator: (val) {
                                  if (val!.isEmpty) return "required field";
                                },
                                decoration: InputDecoration(
                                  hintText: "Contact Number",
                                ),
                              ),
                            ),
                          ),
                          SizedBox(width: 10),
                          Expanded(
                            flex: 1,
                            child: Container(
                              height: height(context) * .1,
                              child: TextFormField(
                                controller: gender,
                                validator: (val) {
                                  if (val!.isEmpty) return "required field";
                                },
                                decoration: InputDecoration(
                                  hintText: "Gender",
                                ),
                              ),
                            ),
                          ),
                        ],
                      ),
                      Row(
                        children: [
                          Expanded(
                            flex: 1,
                            child: Container(
                              height: height(context) * .1,
                              child: TextFormField(
                                controller: dateOfBirth,
                                validator: (val) {
                                  if (val!.isEmpty) return "required field";
                                },
                                decoration: InputDecoration(
                                  hintText: "Date of Birth",
                                ),
                              ),
                            ),
                          ),
                          SizedBox(width: 10),
                          Expanded(
                            flex: 1,
                            child: Container(
                              height: height(context) * .1,
                              child: TextFormField(
                                controller: cgpa,
                                validator: (val) {
                                  if (val!.isEmpty) return "required field";
                                },
                                decoration: InputDecoration(
                                  hintText: "CGPA",
                                ),
                              ),
                            ),
                          ),
                        ],
                      ),
                      Row(
                        children: [
                          Expanded(
                            flex: 1,
                            child: Container(
                              height: height(context) * .1,
                              child: TextFormField(
                                controller: educationalInstitute,
                                validator: (val) {
                                  if (val!.isEmpty) return "required field";
                                },
                                decoration: InputDecoration(
                                  hintText: "Educational Institute",
                                ),
                              ),
                            ),
                          ),
                          SizedBox(width: 10),
                          Expanded(
                            flex: 1,
                            child: Container(
                              height: height(context) * .1,
                              child: TextFormField(
                                controller: passingYear,
                                validator: (val) {
                                  if (val!.isEmpty) return "required field";
                                },
                                decoration: InputDecoration(
                                  hintText: "Passing Year",
                                ),
                              ),
                            ),
                          ),
                        ],
                      ),
                      Row(
                        children: [
                          Expanded(
                            flex: 1,
                            child: Container(
                              height: height(context) * .1,
                              child: TextFormField(
                                controller: degreeName,
                                validator: (val) {
                                  if (val!.isEmpty) return "required field";
                                },
                                decoration: InputDecoration(
                                  hintText: "Degree Name",
                                ),
                              ),
                            ),
                          ),
                          SizedBox(width: 10),
                          Expanded(
                            flex: 1,
                            child: Container(
                              height: height(context) * .1,
                              child: TextFormField(
                                controller: presentAddress,
                                validator: (val) {
                                  if (val!.isEmpty) return "required field";
                                },
                                decoration: InputDecoration(
                                  hintText: "Present Address",
                                ),
                              ),
                            ),
                          ),
                        ],
                      ),
                      Padding(
                        padding: const EdgeInsets.all(8.0),
                        child: ElevatedButton(
                          onPressed: () {
                            if (createTraineeFormKey.currentState!.validate()) {
                              print("yeeee all ok---image diso to?");

                              var traineeData = {
                                "name": name.text,
                                "email": email.text,
                                "password": password.text,
                                "dateOfBirth": dateOfBirth.text,
                                "contact": contact.text,
                                "cgpa": cgpa.text,
                                "educationalInstitute": educationalInstitute.text,
                                "gender": gender.text,
                                "passingYear": passingYear.text,
                                "presentAddress": presentAddress.text,
                                "degreeName": degreeName.text,
                                "role": "TRAINEE",
                              };
                              createUserProvider.createTrainee(traineeData, context);
                            }
                          },
                          style: ElevatedButton.styleFrom(
                            backgroundColor: Colors.black,
                          ),
                          child: Text(
                            "Register",
                            style: TextStyle(
                              color: sweetYellow,
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
        ]);
      },
    );
  }
}
