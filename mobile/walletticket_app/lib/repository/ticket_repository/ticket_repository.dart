import 'package:walletticket_app/models/login/login_dto.dart';
import 'package:walletticket_app/models/login/login_response.dart';
import 'package:walletticket_app/models/register/register_dto.dart';
import 'package:walletticket_app/models/register/register_response.dart';
import 'package:walletticket_app/models/ticket/ticket_response.dart';

abstract class TicketRepository {
  Future<List<TicketResponse>> getAllbyUser();
  Future<List<TicketResponse>> getAllbyUserAndFavorite();
  Future addFavorite(int id);
  Future deleteFavorite(int id);
  Future deleteTicket(int id);
}
