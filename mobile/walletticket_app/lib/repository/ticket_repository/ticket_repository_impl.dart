import 'dart:convert';
import 'dart:developer';
import 'package:http/http.dart';
import 'package:http/http.dart' as http;
import 'package:http_parser/http_parser.dart';
import 'package:walletticket_app/models/login/login_dto.dart';
import 'package:walletticket_app/models/login/login_response.dart';
import 'package:walletticket_app/models/register/register_dto.dart';
import 'package:walletticket_app/models/register/register_response.dart';
import 'package:walletticket_app/models/ticket/ticket_response.dart';

import '../constants.dart';
import 'ticket_repository.dart';

class TicketRepositoryImpl extends TicketRepository {
  final Client _client = Client();

  @override
  Future<List<TicketResponse>> getAllbyUser() async {
    final response = await _client.get(
        Uri.parse('http://10.0.2.2:8080/ticket/all'),
        headers: {'Authorization': 'Bearer ${Constant.token}'});
    if (response.statusCode == 200) {
      return (json.decode(response.body) as List)
          .map((i) => TicketResponse.fromJson(i))
          .toList();
    } else {
      throw Exception('Fail to load ticket');
    }
  }

  @override
  Future addFavorite(int id) async {
    final response = await _client.post(
        Uri.parse('http://10.0.2.2:8080/ticket/favorite/add/${id}'),
        headers: {'Authorization': 'Bearer ${Constant.token}'});
    if (response.statusCode == 204) {
    } else {
      throw Exception('Fail to load ticket');
    }
  }

  @override
  Future deleteFavorite(int id) async {
    final response = await _client.post(
        Uri.parse('http://10.0.2.2:8080/ticket/favorite/delete/${id}'),
        headers: {'Authorization': 'Bearer ${Constant.token}'});
    if (response.statusCode == 204) {
    } else {
      throw Exception('Fail to load ticket');
    }
  }

  @override
  Future deleteTicket(int id) async {
    final response = await _client.delete(
        Uri.parse('http://10.0.2.2:8080/ticket/${id}'),
        headers: {'Authorization': 'Bearer ${Constant.token}'});
    if (response.statusCode == 204) {
    } else {
      throw Exception('Fail to load ticket');
    }
  }
}
