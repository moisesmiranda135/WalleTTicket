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
import 'package:walletticket_app/styles/styles.dart';

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
            color: Colors.white,
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
        child: SingleChildScrollView(
          child: Container(
            color: Colors.white,
            child: Center(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Image.asset(
                    "assets/images/WalleTTicket-logo.png",
                    width: 300,
                  ),
                  Container(
                      margin: const EdgeInsets.only(bottom: 40),
                      width: 300,
                      child: Text("- Digitaliza tu vida - ".toUpperCase(),
                          textAlign: TextAlign.center,
                          style: const TextStyle(
                              fontSize: 20, color: Colors.blue))),
                  Container(
                    width: WalleTTicketStyle.formFieldWidth,
                    height: WalleTTicketStyle.formFieldHeight,
                    decoration: const BoxDecoration(
                      color: Colors.white,
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
                            ? 'Escribe un email correcto'
                            : null;
                      },
                      decoration: InputDecoration(
                          errorBorder: WalleTTicketStyle.formBorder,
                          focusedErrorBorder: WalleTTicketStyle.formBorder,
                          enabledBorder: WalleTTicketStyle.formBorder,
                          focusedBorder: WalleTTicketStyle.formBorder,
                          contentPadding: WalleTTicketStyle.contentPadding,
                          fillColor: Colors.black,
                          hintText: "Ecribe tu email",
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
                      controller: passwordController,
                      onSaved: (String? value) {
                        // This optional block of code can be used to run
                        // code when the user saves the form.
                      },
                      validator: (value) {
                        return (value == null || value.isEmpty)
                            ? 'Escribe una contraseña'
                            : null;
                      },
                      obscureText: _isObscure,
                      decoration: InputDecoration(
                        errorBorder: WalleTTicketStyle.formBorder,
                        focusedErrorBorder: WalleTTicketStyle.formBorder,
                        enabledBorder: WalleTTicketStyle.formBorder,
                        focusedBorder: WalleTTicketStyle.formBorder,
                        contentPadding: WalleTTicketStyle.contentPadding,
                        fillColor: Colors.white,
                        hintText: "Contraseña",
                        hintStyle: const TextStyle(color: Colors.grey),
                        suffixIcon: IconButton(
                          padding: const EdgeInsets.only(right: 30),
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
                        final loginDto = LoginDto(
                            email: emailController.text,
                            password: passwordController.text);
                        BlocProvider.of<LoginBloc>(context)
                            .add(DoLoginEvent(loginDto));
                      }
                    },
                    child: Container(
                        height: 45,
                        width: 200,
                        margin:
                            const EdgeInsets.only(top: 20, left: 30, right: 30),
                        padding: const EdgeInsets.symmetric(
                          vertical: 13,
                        ),
                        decoration: BoxDecoration(
                            color: WalleTTicketStyle.blueButtonB,
                            borderRadius: BorderRadius.circular(18)),
                        child: Text(
                          'Acceder'.toUpperCase(),
                          style: const TextStyle(color: Colors.white),
                          textAlign: TextAlign.center,
                        )),
                  ),
                  Container(
                    margin: const EdgeInsets.symmetric(vertical: 20),
                    width: 200,
                    height: 1,
                    color: WalleTTicketStyle.blueButtonB,
                  ),
                  RichText(
                      text: TextSpan(children: <TextSpan>[
                    const TextSpan(
                      text: '¿No te has registrado?',
                      style: TextStyle(
                        fontSize: 15,
                        color: Colors.black54,
                      ),
                    ),
                    TextSpan(
                        text: '    Registrate',
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
                  ]))
                ],
              ),
            ),
          ),
        ));
  }
}
