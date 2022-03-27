import 'package:walletticket_app/models/login/login_dto.dart';
import 'package:walletticket_app/models/login/login_response.dart';
import 'package:walletticket_app/models/register/register_dto.dart';
import 'package:walletticket_app/models/register/register_response.dart';

abstract class AuthRepository {
  Future<LoginResponse> login(LoginDto loginDto);

  Future<RegisterResponse> register(RegisterDto registerDto);

  // Future<AboutMeResponse> fetchAboutMe();
}
