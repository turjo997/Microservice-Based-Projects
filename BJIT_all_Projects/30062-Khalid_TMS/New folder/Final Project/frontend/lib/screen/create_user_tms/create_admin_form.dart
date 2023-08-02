import 'package:animated_text_kit/animated_text_kit.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../../components/box_decorations.dart';
import '../../components/color.dart';
import '../../components/screen_size.dart';
import '../../components/text_style.dart';
import '../../provider/create_user_provider.dart';

class CreateAdminForm extends StatelessWidget {
  CreateAdminForm({
    super.key,
  });

  final name = TextEditingController();
  final email = TextEditingController();
  final password = TextEditingController();
  final contact = TextEditingController();
  final gender = TextEditingController();
  final createAdminFormKey = GlobalKey<FormState>();


  @override
  Widget build(BuildContext context) {
    return Consumer<CreateUserProvider>(
        builder: (context, createUserProvider, child) {
      return Column(
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Expanded(
              flex: 1,
              child: Padding(
                padding: const EdgeInsets.all(8.0),
                child: Text("Register Admin",
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
              key: createAdminFormKey,
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
                                decoration: InputDecoration(hintText: "Name"),
                              ),
                            ),
                          ),
                          SizedBox(
                            width: 10,
                          ),
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
                                      "select image",
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
                      Container(
                        width: width(context) * .5,
                        height: height(context) * .1,
                        child: TextFormField(
                          controller: email,
                          validator: (val) {
                            if (val!.isEmpty) return "required field";
                          },
                          decoration: InputDecoration(hintText: "Email"),
                        ),
                      ),
                      Container(
                        //color: Colors.red,
                        width: width(context) * .5,
                        height: height(context) * .1,
                        child: TextFormField(
                          controller: password,
                          validator: (val) {
                            if (val!.isEmpty) return "required field";
                          },
                          decoration: InputDecoration(hintText: "Password"),
                        ),
                      ),
                      Row(
                        children: [
                          Expanded(
                            flex: 1,
                            child: Container(
                              //color: Colors.red,
                              height: height(context) * .1,
                              child: TextFormField(
                                controller: contact,
                                validator: (val) {
                                  if (val!.isEmpty) return "required field";
                                },
                                decoration: InputDecoration(hintText: "Contact"),
                              ),
                            ),
                          ),
                          SizedBox(
                            width: 10,
                          ),
                          Expanded(
                            flex: 1,
                            child: Container(
                              //color: Colors.red,
                              height: height(context) * .1,
                              child: TextFormField(
                                controller: gender,
                                validator: (val) {
                                  if (val!.isEmpty) return "required field";
                                },
                                decoration: InputDecoration(hintText: "Gender"),
                              ),
                            ),
                          ),
                        ],
                      ),
                      Padding(
                        padding: const EdgeInsets.all(8.0),
                        child: ElevatedButton(
                          onPressed: () {
                            print("yoo");

                            if (createAdminFormKey.currentState!.validate()) {
                              print("required field");

                              var adminData = {
                                "name": name.text,
                                "email": email.text,
                                "password": password.text,
                                "contact": contact.text,
                                "gender": gender.text,
                                "role": "ADMIN",
                              };
                              createUserProvider.createAdmin(adminData, context);
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
                      )
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
