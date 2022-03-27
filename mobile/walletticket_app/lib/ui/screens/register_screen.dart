import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:walletticket_app/bloc/register/register_bloc.dart';
import 'package:walletticket_app/models/register/register_dto.dart';
import 'package:walletticket_app/repository/auth_repository/auth_repository.dart';
import 'package:walletticket_app/repository/auth_repository/auth_repository_impl.dart';
import 'package:walletticket_app/ui/screens/login_screen.dart';

import 'menu_screen.dart';

class RegisterScreen extends StatefulWidget {
  const RegisterScreen({Key? key}) : super(key: key);

  @override
  _RegisterScreenState createState() => _RegisterScreenState();
}

class _RegisterScreenState extends State<RegisterScreen> {
  late bool _isObscure;

  late AuthRepository authRepository;
  final _formKey = GlobalKey<FormState>();
  TextEditingController nameController = TextEditingController();
  TextEditingController lastNameController = TextEditingController();
  TextEditingController emailController = TextEditingController();
  TextEditingController passwordController = TextEditingController();
  TextEditingController password2Controller = TextEditingController();

  @override
  void initState() {
    super.initState();
    _isObscure = true;
    authRepository = AuthRepositoryImpl();
  }

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
        create: (context) {
          return RegisterBloc(authRepository);
        },
        child: _createBody(context));
  }

  Widget _createBody(BuildContext context) {
    return Scaffold(
      body: Container(
        margin: MediaQuery.of(context).padding,
        child: Center(
          child: Container(
              child: BlocConsumer<RegisterBloc, RegisterState>(
            listenWhen: (context, state) {
              return state is RegisterSuccessState ||
                  state is RegisterErrorState;
            },
            listener: (context, state) {
              if (state is RegisterSuccessState) {
                Navigator.push(
                    context,
                    MaterialPageRoute(
                        builder: (context) => const LoginScreen()));
              } else if (state is RegisterErrorState) {
                _showSnackbar(context, state.message);
              }
            },
            buildWhen: (context, state) {
              return state is RegisterInitialState ||
                  state is RegisterLoadingState;
            },
            builder: (context, state) {
              if (state is RegisterInitialState) {
                return _buildForm(context);
              } else if (state is RegisterLoadingState) {
                return const Center(
                  child: CircularProgressIndicator(),
                );
              } else {
                return _buildForm(context);
              }
            },
          )),
        ),
      ),
    );
  }

  void _showSnackbar(BuildContext context, String message) {
    final snackBar = SnackBar(
      content: Text(message),
    );
    ScaffoldMessenger.of(context).showSnackBar(snackBar);
  }

  @override
  Widget _buildForm(BuildContext context) {
    return Container(
        color: Colors.grey[200],
        height: MediaQuery.of(context).size.height,
        child: SingleChildScrollView(
            child: Stack(children: [
          Form(
              key: _formKey,
              child: Container(
                margin: const EdgeInsets.only(
                  top: 20,
                ),
                child: Center(
                    child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Container(
                        margin: const EdgeInsets.only(top: 10, bottom: 10),
                        width: 300,
                        child: Text("WalleTTicket",
                            textAlign: TextAlign.center,
                            style: TextStyle(
                                fontSize: 50, color: Colors.grey[900]))),
                    Container(
                        margin: const EdgeInsets.only(bottom: 20),
                        width: 300,
                        child: Text("Registrate para comenzar",
                            textAlign: TextAlign.center,
                            style: TextStyle(
                                fontFamily: 'MyArmaApp',
                                fontSize: 30,
                                color: Colors.grey[700]))),
                    Container(
                      margin: const EdgeInsets.only(
                        top: 20,
                      ),
                      width: 330,
                      decoration: BoxDecoration(
                        color: Colors.white,
                        border: Border.all(color: Colors.white),
                        borderRadius: const BorderRadius.all(
                          Radius.circular(10),
                        ),
                      ),
                      child: TextFormField(
                        controller: nameController,
                        onSaved: (String? value) {},
                        decoration: InputDecoration(
                            enabledBorder: OutlineInputBorder(
                              borderRadius: BorderRadius.circular(15.0),
                              borderSide: const BorderSide(
                                color: Colors.transparent,
                                width: 2.0,
                              ),
                            ),
                            focusedBorder: OutlineInputBorder(
                              borderRadius: BorderRadius.circular(15.0),
                              borderSide: const BorderSide(
                                color: Colors.transparent,
                                width: 2.0,
                              ),
                            ),
                            contentPadding: const EdgeInsets.all(20),
                            fillColor: Colors.white,
                            hintText: "Enter your name",
                            hintStyle: const TextStyle(color: Colors.grey)),
                      ),
                    ),
                    Container(
                      margin: const EdgeInsets.only(
                        top: 20,
                      ),
                      width: 330,
                      decoration: BoxDecoration(
                        color: Colors.white,
                        border: Border.all(color: Colors.white),
                        borderRadius: const BorderRadius.all(
                          Radius.circular(10),
                        ),
                      ),
                      child: TextFormField(
                        controller: lastNameController,
                        onSaved: (String? value) {},
                        decoration: InputDecoration(
                            enabledBorder: OutlineInputBorder(
                              borderRadius: BorderRadius.circular(15.0),
                              borderSide: const BorderSide(
                                color: Colors.transparent,
                                width: 2.0,
                              ),
                            ),
                            focusedBorder: OutlineInputBorder(
                              borderRadius: BorderRadius.circular(15.0),
                              borderSide: const BorderSide(
                                color: Colors.transparent,
                                width: 2.0,
                              ),
                            ),
                            contentPadding: const EdgeInsets.all(20),
                            fillColor: Colors.white,
                            hintText: "Enter your lastName",
                            hintStyle: const TextStyle(color: Colors.grey)),
                      ),
                    ),
                    Container(
                      margin: const EdgeInsets.only(
                        top: 20,
                      ),
                      width: 330,
                      decoration: BoxDecoration(
                        color: Colors.white,
                        border: Border.all(color: Colors.white),
                        borderRadius: const BorderRadius.all(
                          Radius.circular(10),
                        ),
                      ),
                      child: TextFormField(
                        controller: emailController,
                        onSaved: (String? value) {
                          // This optional block of code can be used to run
                          // code when the user saves the form.
                        },
                        validator: (String? value) {
                          return (value == null || !value.contains('@'))
                              ? 'Do not use the @ char.'
                              : null;
                        },
                        decoration: InputDecoration(
                            enabledBorder: OutlineInputBorder(
                              borderRadius: BorderRadius.circular(15.0),
                              borderSide: const BorderSide(
                                color: Colors.transparent,
                                width: 2.0,
                              ),
                            ),
                            focusedBorder: OutlineInputBorder(
                              borderRadius: BorderRadius.circular(15.0),
                              borderSide: const BorderSide(
                                color: Colors.transparent,
                                width: 2.0,
                              ),
                            ),
                            contentPadding: const EdgeInsets.all(20),
                            fillColor: Colors.white,
                            hintText: "Enter Email",
                            hintStyle: const TextStyle(color: Colors.grey)),
                      ),
                    ),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        Container(
                          margin: const EdgeInsets.only(top: 20, right: 10),
                          width: 150,
                          decoration: BoxDecoration(
                            color: Colors.white,
                            border: Border.all(color: Colors.white),
                            borderRadius: const BorderRadius.all(
                              Radius.circular(10),
                            ),
                          ),
                          child: TextFormField(
                            controller: passwordController,
                            onSaved: (String? value) {},
                            validator: (value) {
                              if (value == null || value.isEmpty) {
                                return 'Please enter some text';
                              }
                              return null;
                            },
                            decoration: InputDecoration(
                                enabledBorder: OutlineInputBorder(
                                  borderRadius: BorderRadius.circular(15.0),
                                  borderSide: const BorderSide(
                                    color: Colors.transparent,
                                    width: 2.0,
                                  ),
                                ),
                                focusedBorder: OutlineInputBorder(
                                  borderRadius: BorderRadius.circular(15.0),
                                  borderSide: const BorderSide(
                                    color: Colors.transparent,
                                    width: 2.0,
                                  ),
                                ),
                                contentPadding: const EdgeInsets.all(20),
                                fillColor: Colors.white,
                                hintText: "Enter LastName",
                                hintStyle: const TextStyle(color: Colors.grey)),
                          ),
                        ),
                        Row(
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            Container(
                              margin: const EdgeInsets.only(top: 20, right: 10),
                              width: 150,
                              decoration: BoxDecoration(
                                color: Colors.white,
                                border: Border.all(color: Colors.white),
                                borderRadius: const BorderRadius.all(
                                  Radius.circular(10),
                                ),
                              ),
                              child: TextFormField(
                                controller: password2Controller,
                                onSaved: (String? value) {
                                  // This optional block of code can be used to run
                                  // code when the user saves the form.
                                },
                                validator: (value) {
                                  if (value == null || value.isEmpty) {
                                    return 'Please enter some text';
                                  }
                                  return null;
                                },
                                obscureText: _isObscure,
                                decoration: InputDecoration(
                                  enabledBorder: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(15.0),
                                    borderSide: const BorderSide(
                                      color: Colors.transparent,
                                      width: 2.0,
                                    ),
                                  ),
                                  focusedBorder: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(15.0),
                                    borderSide: const BorderSide(
                                      color: Colors.transparent,
                                      width: 2.0,
                                    ),
                                  ),
                                  contentPadding: const EdgeInsets.all(20),
                                  fillColor: Colors.white,
                                  hintText: "Password",
                                  hintStyle:
                                      const TextStyle(color: Colors.grey),
                                  suffixIcon: IconButton(
                                    icon: Icon(_isObscure
                                        ? Icons.visibility
                                        : Icons.visibility_off),
                                    onPressed: () {
                                      setState(() {
                                        _isObscure = !_isObscure;
                                      });
                                    },
                                  ),
                                ),
                              ),
                            ),
                          ],
                        ),
                      ],
                    ),
                    GestureDetector(
                      onTap: () {
                        if (_formKey.currentState!.validate()) {
                          final registerDto = RegisterDto(
                            name: nameController.text,
                            lastName: lastNameController.text,
                            email: emailController.text,
                            password: passwordController.text,
                          );
                          BlocProvider.of<RegisterBloc>(context)
                              .add(DoRegisterEvent(registerDto));
                        }
                      },
                      child: Container(
                          height: 50,
                          width: MediaQuery.of(context).size.width,
                          margin: const EdgeInsets.only(
                              top: 10, left: 30, right: 30),
                          padding: const EdgeInsets.only(
                            top: 15,
                          ),
                          decoration: BoxDecoration(
                              color: Colors.black54,
                              border: Border.all(color: Colors.white, width: 2),
                              borderRadius: BorderRadius.circular(10)),
                          child: Text(
                            'Register'.toUpperCase(),
                            style: const TextStyle(color: Colors.white),
                            textAlign: TextAlign.center,
                          )),
                    ),
                  ],
                )),
              ))
        ])));
  }

  void uploadImage(String file) {
    RegisterDto dto = RegisterDto(
      name: nameController.text,
      lastName: lastNameController.text,
      email: emailController.text,
      password: passwordController.text,
    );

    AuthRepositoryImpl().register(dto);
  }
}
