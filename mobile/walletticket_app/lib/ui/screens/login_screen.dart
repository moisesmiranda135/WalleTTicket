import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:walletticket_app/bloc/login/login_bloc.dart';
import 'package:walletticket_app/models/login/login_dto.dart';
import 'package:walletticket_app/repository/auth_repository/auth_repository.dart';
import 'package:walletticket_app/repository/auth_repository/auth_repository_impl.dart';
import 'package:walletticket_app/repository/constants.dart';
import 'package:walletticket_app/ui/screens/register_screen.dart';

import 'menu_screen.dart';

class LoginScreen extends StatefulWidget {
  const LoginScreen({Key? key}) : super(key: key);

  @override
  _LoginScreenState createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  late bool _isObscure;

  late AuthRepository authRepository;
  final _formKey = GlobalKey<FormState>();
  TextEditingController emailController = TextEditingController();
  TextEditingController passwordController = TextEditingController();

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
          return LoginBloc(authRepository);
        },
        child: _createBody(context));
  }

  _createBody(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Container(
            color: Colors.grey[200],
            padding: const EdgeInsets.all(20),
            child: BlocConsumer<LoginBloc, LoginState>(
                listenWhen: (context, state) {
              return state is LoginSuccessState || state is LoginErrorState;
            }, listener: (context, state) async {
              if (state is LoginSuccessState) {
                SharedPreferences preferences =
                    await SharedPreferences.getInstance();
                preferences.setString(
                    Constant.token, state.loginResponse.token);
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => const MenuScreen()),
                );
              } else if (state is LoginErrorState) {
                _showSnackbar(context, state.message);
              }
            }, buildWhen: (context, state) {
              return state is LoginInitialState || state is LoginLoadingState;
            }, builder: (ctx, state) {
              if (state is LoginInitialState) {
                return buildForm(ctx);
              } else if (state is LoginLoadingState) {
                return const Center(child: CircularProgressIndicator());
              } else {
                return buildForm(ctx);
              }
            })),
      ),
    );
  }

  void _showSnackbar(BuildContext context, String message) {
    final snackBar = SnackBar(
      content: Text(message),
    );
    ScaffoldMessenger.of(context).showSnackBar(snackBar);
  }

  Widget buildForm(BuildContext context) {
    return Form(
        key: _formKey,
        child: Container(
          color: Colors.grey[200],
          child: Center(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Container(
                    margin: const EdgeInsets.only(top: 10, bottom: 60),
                    width: 300,
                    child: Text("WalleTTicket",
                        textAlign: TextAlign.center,
                        style:
                            TextStyle(fontSize: 50, color: Colors.grey[900]))),
                Container(
                    margin: const EdgeInsets.only(bottom: 20),
                    width: 300,
                    child: Text("Digitaliza tu vida".toUpperCase(),
                        textAlign: TextAlign.center,
                        style:
                            TextStyle(fontSize: 30, color: Colors.grey[700]))),
                Container(
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
                        hintText: "Enter email",
                        hintStyle: const TextStyle(color: Colors.grey)),
                  ),
                ),
                Container(
                  margin: const EdgeInsets.only(
                    top: 15,
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
                    controller: passwordController,
                    onSaved: (String? value) {
                      // This optional block of code can be used to run
                      // code when the user saves the form.
                    },
                    validator: (value) {
                      return (value == null || value.isEmpty)
                          ? 'Write a password'
                          : null;
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
                const Padding(
                  padding: EdgeInsets.only(left: 210, top: 8, bottom: 30),
                  child: Text('Recovery password',
                      style: TextStyle(
                          fontSize: 13,
                          fontWeight: FontWeight.bold,
                          color: Colors.grey)),
                ),
                GestureDetector(
                  onTap: () {
                    if (_formKey.currentState!.validate()) {
                      final loginDto = LoginDto(
                          email: emailController.text,
                          password: passwordController.text);
                      BlocProvider.of<LoginBloc>(context)
                          .add(DoLoginEvent(loginDto));
                    }
                  },
                  child: Container(
                      height: 50,
                      width: MediaQuery.of(context).size.width,
                      margin:
                          const EdgeInsets.only(top: 10, left: 30, right: 30),
                      padding: const EdgeInsets.only(
                        top: 15,
                      ),
                      decoration: BoxDecoration(
                          color: Colors.black54,
                          border: Border.all(color: Colors.white, width: 2),
                          borderRadius: BorderRadius.circular(10)),
                      child: Text(
                        'Sign In'.toUpperCase(),
                        style: const TextStyle(color: Colors.white),
                        textAlign: TextAlign.center,
                      )),
                ),
                Container(
                    padding: const EdgeInsets.only(top: 30),
                    child: RichText(
                        text: TextSpan(children: <TextSpan>[
                      const TextSpan(
                        text: 'Not a member?',
                        style: TextStyle(
                          fontSize: 15,
                          color: Colors.black54,
                        ),
                      ),
                      TextSpan(
                          text: '    Register now',
                          recognizer: TapGestureRecognizer()
                            ..onTap = () => Navigator.push(
                                  context,
                                  MaterialPageRoute(
                                      builder: (context) =>
                                          const RegisterScreen()),
                                ),
                          style: const TextStyle(
                            fontSize: 15,
                            color: Colors.blue,
                            fontFamily: 'MyArmaApp',
                          ))
                    ])))
              ],
            ),
          ),
        ));
  }
}
