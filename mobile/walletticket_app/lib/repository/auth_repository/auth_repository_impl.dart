import 'dart:convert';
import 'package:http/http.dart';
import 'package:http/http.dart' as http;
import 'package:http_parser/http_parser.dart';
import 'package:walletticket_app/models/login/login_dto.dart';
import 'package:walletticket_app/models/login/login_response.dart';
import 'package:walletticket_app/models/register/register_dto.dart';
import 'package:walletticket_app/models/register/register_response.dart';

import 'auth_repository.dart';

class AuthRepositoryImpl extends AuthRepository {
  final Client _client = Client();

  @override
  Future<LoginResponse> login(LoginDto loginDto) async {
    Map<String, String> headers = {'Content-Type': 'application/json'};

    final response = await _client.post(
        Uri.parse('http://10.0.2.2:8080/auth/login'),
        headers: headers,
        body: jsonEncode(loginDto.toJson()));
    if (response.statusCode == 201) {
      return LoginResponse.fromJson(json.decode(response.body));
    } else {
      throw Exception('Fail to login');
    }
  }

  @override
  Future<RegisterResponse> register(RegisterDto registerDto) async {
    Map<String, String> headers = {'Content-Type': 'multipart/form-data'};

    var uri = Uri.parse('http://10.0.2.2:8080/auth/register/user');

    var request = http.MultipartRequest('POST', uri)
      ..files.add(await http.MultipartFile.fromString(
          'json', jsonEncode(registerDto.toJson()),
          contentType: MediaType('application', 'json')))
      ..headers.addAll(headers);

    var response = await request.send();

    final res = await response.stream.bytesToString();

    if (response.statusCode == 200) {
      return RegisterResponse.fromJson(await jsonDecode(res));
    } else {
      throw Exception('Fail to register');
    }
  }

  /*
  @override
  Future<AboutMeResponse> fetchAboutMe() async {
    final response = await _client.get(Uri.parse('http://10.0.2.2:8080/me'),
        headers: {'Authorization': 'Bearer ${Constant.token}'});
    if (response.statusCode == 200) {
      return AboutMeResponse.fromJson(json.decode(response.body));
    } else {
      throw Exception('Fail to load auth');
    }
  }
  */
}
