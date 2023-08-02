import 'dart:convert';
import 'package:jwt_decoder/jwt_decoder.dart';
import 'package:http/http.dart' as http;
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:quickalert/models/quickalert_type.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:training_management_system/provider/login_provider.dart';
import 'package:universal_html/html.dart' as html;

import '../../components/color.dart';
import '../../components/quickAlert.dart';


class Login extends StatefulWidget {
  const Login({Key? key}) : super(key: key);

  @override
  State<Login> createState() => _LoginState();
}

class _LoginState extends State<Login> with SingleTickerProviderStateMixin {
  final _formKey = GlobalKey<FormState>();
  final _email = TextEditingController();
  final _password = TextEditingController();
  late AnimationController _animationController;
  late Animation<double> _animation;

  @override
  void initState() {
    _animationController = AnimationController(
      vsync: this,
      duration: Duration(milliseconds: 500),
    );
    _animation = Tween<double>(begin: 1.0, end: 1.1).animate(_animationController);
    super.initState();
  }

  @override
  void dispose() {
    _animationController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Container(
          width: 350,
          padding: EdgeInsets.all(24),
          decoration: BoxDecoration(
            color: Colors.white,
            borderRadius: BorderRadius.circular(16),
            boxShadow: [
              BoxShadow(
                color: Colors.black.withOpacity(0.1),
                blurRadius: 10,
                offset: Offset(0, 4),
              ),
            ],
          ),
          child: Consumer<LoginProvider>(
            builder: (context, loginProvider, child) {
              return Form(
                key: _formKey,
                child: Column(
                  mainAxisSize: MainAxisSize.min,
                  crossAxisAlignment: CrossAxisAlignment.stretch,
                  children: [
                    Text(
                      'TMS', // Your app title
                      textAlign: TextAlign.center,
                      style: TextStyle(
                        fontSize: 50,
                        fontWeight: FontWeight.w900,
                        color: sweetYellow, // Customize the color as per your design
                      ),
                    ),
                    SizedBox(height: 10),
                    Text(
                      'Your Favorite Training Management System',
                      textAlign: TextAlign.center,
                      style: TextStyle(
                        fontSize: 14,
                        color: Colors.grey[600],
                      ),
                    ),
                    SizedBox(height: 20),
                    TextFormField(
                      validator: (val) {
                        if (val!.isEmpty) {
                          return 'Please enter your email';
                        }
                      },
                      controller: _email,
                      decoration: InputDecoration(
                        labelText: 'Email or Phone Number',
                        border: OutlineInputBorder(),
                      ),
                    ),
                    SizedBox(height: 16),
                    TextFormField(
                      validator: (val) {
                        if (val!.isEmpty) {
                          return 'Please enter your password';
                        }
                      },
                      controller: _password,
                      obscureText: true,
                      decoration: InputDecoration(
                        labelText: 'Password',
                        border: OutlineInputBorder(),
                      ),
                    ),
                    SizedBox(height: 10),
                    Align(
                      alignment: Alignment.centerRight,
                      child: TextButton(
                        onPressed: () {
                          // Implement forgot password functionality here
                        },
                        child: Text(
                          'Forgot Password?',
                          style: TextStyle(
                            color: Colors.blueGrey,
                            fontSize: 14,
                          ),
                        ),
                      ),
                    ),
                    const SizedBox(height: 20),
                    ElevatedButton(
                      onPressed: () async {
                        if (_formKey.currentState!.validate()) {
                          _animationController.forward().then((_) async {
                            await loginProvider.getRole(_email.text,_password.text, context);

                            if (loginProvider.role == "TRAINEE" && loginProvider.statusCode== 200) {

                              Future.delayed(const Duration(seconds: 1), (){
                                Navigator.pushReplacementNamed(context, "LandingScreenTrainee");
                              });
                            } else if (loginProvider.role == "ADMIN" && loginProvider.statusCode== 200) {
                              Future.delayed(const Duration(seconds: 1), (){
                                    Navigator.pushReplacementNamed(context, "AdminHome",);
                              });
                            } else if (loginProvider.role == "TRAINER" && loginProvider.statusCode== 200) {
                              Future.delayed(const Duration(seconds: 1), () {
                                Navigator.pushReplacementNamed(context, "LandingScreenTrainer");
                              });

                            } else {
                              Noti notification = new Noti();
                              notification.noti(context, QuickAlertType.error, "Login Failed, Check Credentials");
                            }
                          });
                        } else {
                          print("Form invalid");
                        }
                      },
                      style: ElevatedButton.styleFrom(
                        backgroundColor: Colors.black,
                        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(8)),
                        padding: EdgeInsets.symmetric(horizontal: 32, vertical: 14),
                      ),
                      child: Text(
                        "Log In",
                        style: TextStyle(
                          color: sweetYellow,
                          fontSize: 18,
                        ),

                      ),
                    )
                  ],
                ),
              );
            },
          ),
        ),
      ),
    );
  }
}
