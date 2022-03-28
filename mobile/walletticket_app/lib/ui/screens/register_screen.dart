// ignore_for_file: unrelated_type_equality_checks

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:walletticket_app/bloc/register/register_bloc.dart';
import 'package:walletticket_app/models/register/register_dto.dart';
import 'package:walletticket_app/repository/auth_repository/auth_repository.dart';
import 'package:walletticket_app/repository/auth_repository/auth_repository_impl.dart';
import 'package:walletticket_app/styles/styles.dart';
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
        color: Colors.white,
        height: MediaQuery.of(context).size.height,
        child: SingleChildScrollView(
            child: Stack(children: [
          Center(
            child: Form(
                key: _formKey,
                child: Center(
                    child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Image.asset(
                      "assets/images/WalleTTicket-logo.png",
                      width: 300,
                    ),
                    Container(
                        margin: const EdgeInsets.only(bottom: 30),
                        width: 300,
                        child: Text(
                            "- Registrate para comenzar - ".toUpperCase(),
                            textAlign: TextAlign.center,
                            style: const TextStyle(
                                fontSize: 18, color: Colors.blue))),
                    Container(
                      width: WalleTTicketStyle.formFieldWidth,
                      height: WalleTTicketStyle.formFieldHeight,
                      decoration: BoxDecoration(
                        color: Colors.white,
                        border: Border.all(color: Colors.white),
                        borderRadius: const BorderRadius.all(
                          Radius.circular(10),
                        ),
                      ),
                      child: TextFormField(
                        textAlignVertical: TextAlignVertical.bottom,
                        controller: nameController,
                        onSaved: (String? value) {},
                        decoration: InputDecoration(
                            enabledBorder: WalleTTicketStyle.formBorder,
                            focusedBorder: WalleTTicketStyle.formBorder,
                            contentPadding: WalleTTicketStyle.contentPadding,
                            fillColor: Colors.white,
                            hintText: "Escribe tu nombre",
                            hintStyle: const TextStyle(color: Colors.grey)),
                      ),
                    ),
                    Container(
                      margin: const EdgeInsets.only(
                        top: 15,
                      ),
                      width: WalleTTicketStyle.formFieldWidth,
                      height: WalleTTicketStyle.formFieldHeight,
                      decoration: BoxDecoration(
                        color: Colors.white,
                        border: Border.all(color: Colors.white),
                        borderRadius: const BorderRadius.all(
                          Radius.circular(10),
                        ),
                      ),
                      child: TextFormField(
                        textAlignVertical: TextAlignVertical.bottom,
                        controller: lastNameController,
                        onSaved: (String? value) {},
                        decoration: InputDecoration(
                            enabledBorder: WalleTTicketStyle.formBorder,
                            focusedBorder: WalleTTicketStyle.formBorder,
                            contentPadding: WalleTTicketStyle.contentPadding,
                            fillColor: Colors.white,
                            hintText: "Escribe tus apellidos",
                            hintStyle: const TextStyle(color: Colors.grey)),
                      ),
                    ),
                    Container(
                      margin: const EdgeInsets.only(
                        top: 15,
                      ),
                      width: WalleTTicketStyle.formFieldWidth,
                      height: WalleTTicketStyle.formFieldHeight,
                      decoration: BoxDecoration(
                        color: Colors.white,
                        border: Border.all(color: Colors.white),
                        borderRadius: const BorderRadius.all(
                          Radius.circular(10),
                        ),
                      ),
                      child: TextFormField(
                        textAlignVertical: TextAlignVertical.bottom,
                        controller: emailController,
                        onSaved: (String? value) {
                          // This optional block of code can be used to run
                          // code when the user saves the form.
                        },
                        validator: (String? value) {
                          return (value == null || !value.contains('@'))
                              ? 'Escribe un email válido'
                              : null;
                        },
                        decoration: InputDecoration(
                            errorBorder: WalleTTicketStyle.formBorder,
                            focusedErrorBorder: WalleTTicketStyle.formBorder,
                            enabledBorder: WalleTTicketStyle.formBorder,
                            focusedBorder: WalleTTicketStyle.formBorder,
                            contentPadding: WalleTTicketStyle.contentPadding,
                            fillColor: Colors.white,
                            hintText: "Escribe un Email",
                            hintStyle: const TextStyle(color: Colors.grey)),
                      ),
                    ),
                    Container(
                      margin: const EdgeInsets.only(top: 30),
                      width: WalleTTicketStyle.formFieldWidth,
                      height: WalleTTicketStyle.formFieldHeight,
                      decoration: BoxDecoration(
                        color: Colors.white,
                        border: Border.all(color: Colors.white),
                        borderRadius: const BorderRadius.all(
                          Radius.circular(10),
                        ),
                      ),
                      child: TextFormField(
                        textAlignVertical: TextAlignVertical.bottom,
                        controller: passwordController,
                        onSaved: (String? value) {
                          // This optional block of code can be used to run
                          // code when the user saves the form.
                        },
                        validator: (value) {
                          if (value == null || value.isEmpty) {
                            return 'Escribe una contraseña';
                          }
                          return null;
                        },
                        obscureText: _isObscure,
                        decoration: InputDecoration(
                          errorBorder: WalleTTicketStyle.formBorder,
                          focusedErrorBorder: WalleTTicketStyle.formBorder,
                          enabledBorder: WalleTTicketStyle.formBorder,
                          focusedBorder: WalleTTicketStyle.formBorder,
                          contentPadding: WalleTTicketStyle.contentPadding,
                          fillColor: Colors.white,
                          hintText: "Escribe tu contraseña",
                          hintStyle: const TextStyle(color: Colors.grey),
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
                    Container(
                      margin: const EdgeInsets.only(top: 10),
                      width: WalleTTicketStyle.formFieldWidth,
                      height: WalleTTicketStyle.formFieldHeight,
                      decoration: BoxDecoration(
                        color: Colors.white,
                        border: Border.all(color: Colors.white),
                        borderRadius: const BorderRadius.all(
                          Radius.circular(10),
                        ),
                      ),
                      child: TextFormField(
                        textAlignVertical: TextAlignVertical.bottom,
                        controller: password2Controller,
                        onSaved: (String? value) {
                          // This optional block of code can be used to run
                          // code when the user saves the form.
                        },
                        obscureText: _isObscure,
                        decoration: InputDecoration(
                          errorBorder: WalleTTicketStyle.formBorder,
                          focusedErrorBorder: WalleTTicketStyle.formBorder,
                          enabledBorder: WalleTTicketStyle.formBorder,
                          focusedBorder: WalleTTicketStyle.formBorder,
                          contentPadding: WalleTTicketStyle.contentPadding,
                          fillColor: Colors.white,
                          hintText: "Repita la contraseña",
                          hintStyle: const TextStyle(color: Colors.grey),
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
                          height: 45,
                          width: 200,
                          margin: const EdgeInsets.only(
                              top: 20, left: 30, right: 30),
                          padding: const EdgeInsets.symmetric(
                            vertical: 13,
                          ),
                          decoration: BoxDecoration(
                              color: WalleTTicketStyle.blueButtonB,
                              borderRadius: BorderRadius.circular(18)),
                          child: Text(
                            'Register'.toUpperCase(),
                            style: const TextStyle(color: Colors.white),
                            textAlign: TextAlign.center,
                          )),
                    ),
                  ],
                ))),
          )
        ])));
  }
}
