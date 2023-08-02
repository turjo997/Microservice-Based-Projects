import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../../components/color.dart';
import '../../components/screen_size.dart';
import '../../provider/create_user_provider.dart';

class CreateTrainerForm extends StatelessWidget {
  CreateTrainerForm({
    super.key,

  });

  final TextEditingController name = TextEditingController();
  final TextEditingController password = TextEditingController();
  final TextEditingController email = TextEditingController();
  final TextEditingController contact = TextEditingController();
  final TextEditingController gender = TextEditingController();
  final TextEditingController designation = TextEditingController();
  final TextEditingController joiningDate = TextEditingController();
  final TextEditingController yearsOfExperience = TextEditingController();
  final TextEditingController expertises = TextEditingController();
  final TextEditingController presentAddress = TextEditingController();
  final  createTrainerFormKey = GlobalKey<FormState>();

  @override
  Widget build(BuildContext context) {
    return Consumer<CreateUserProvider>(
        builder: (context,createUserProvider,child,){
          return Column(
            children: [
              Expanded(
                  flex: 1,
                  child: Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Text("Register Trainer",
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
                  key: createTrainerFormKey,
                  child: SingleChildScrollView(
                    child: Padding(
                      padding: const EdgeInsets.symmetric(vertical: 100, horizontal: 100),
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
                                      ? Text("Select Image",
                                    style: TextStyle(
                                    color: sweetYellow,
                                  ),)
                                      : Icon(
                                    Icons.thumb_up_sharp,
                                    color: Colors.green,
                                  ),
                                ),
                              ),
                            ],
                          ),
                          Container(
                            width: width(context) * .5,
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
                          Container(
                            width: width(context) * .5,
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
                                    controller: designation,
                                    validator: (val) {
                                      if (val!.isEmpty) return "required field";
                                    },
                                    decoration: InputDecoration(
                                      hintText: "Designation",
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
                                    controller: joiningDate,
                                    validator: (val) {
                                      if (val!.isEmpty) return "required field";
                                    },
                                    decoration: InputDecoration(
                                      hintText: "Joining Date",
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
                                    controller: yearsOfExperience,
                                    validator: (val) {
                                      if (val!.isEmpty) return "required field";
                                    },
                                    decoration: InputDecoration(
                                      hintText: "Years of Experience",
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
                                    controller: expertises,
                                    validator: (val) {
                                      if (val!.isEmpty) return "required field";
                                    },
                                    decoration: InputDecoration(
                                      hintText: "Expertises",
                                    ),
                                  ),
                                ),
                              ),
                            ],
                          ),
                          Container(
                            width: width(context) * .5,
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
                          Padding(
                            padding: const EdgeInsets.all(8.0),
                            child: ElevatedButton(
                              onPressed: () {
                                if (createTrainerFormKey.currentState!.validate()) {

                                  var trainerData = {
                                    "name": name.text,
                                    "email": email.text,
                                    "password": password.text,
                                    "designation": designation.text,
                                    "joiningDate": joiningDate.text,
                                    "yearsOfExperience": yearsOfExperience.text,
                                    "expertises": expertises.text,
                                    "contact": contact.text,
                                    "presentAddress": presentAddress.text,
                                    "role": "TRAINER",
                                  };

                                  createUserProvider.createTrainer(trainerData , context);
                                }
                              },
                              style: ElevatedButton.styleFrom(
                                backgroundColor: Colors.black,
                              ),
                              child: Text("Register Trainer",
                                style: TextStyle(
                                  color: sweetYellow,
                                ),),
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                ),
              ),
            ],
          );
        });
  }
}