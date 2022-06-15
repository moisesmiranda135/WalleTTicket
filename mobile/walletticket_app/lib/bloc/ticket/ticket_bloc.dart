import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:meta/meta.dart';
import 'package:walletticket_app/models/ticket/ticket_response.dart';
import 'package:walletticket_app/repository/ticket_repository/ticket_repository.dart';

part 'ticket_event.dart';
part 'ticket_state.dart';

class TicketBloc extends Bloc<TicketEvent, TicketState> {
  final TicketRepository ticketRepository;

  TicketBloc(this.ticketRepository) : super(TicketInitial()) {
    on<FetchTicketByUser>(_ticketFetched);
    on<FetchTicketByUserAndFavorite>(_ticketFavoriteFetched);
  }

  void _ticketFetched(
      FetchTicketByUser event, Emitter<TicketState> emit) async {
    try {
      final ticket = await ticketRepository.getAllbyUser();
      emit(TicketFetched(ticket));
      return;
    } on Exception catch (e) {
      emit(TicketFetchError(e.toString()));
    }
  }

  void _ticketFavoriteFetched(
      FetchTicketByUserAndFavorite event, Emitter<TicketState> emit) async {
    try {
      final ticket = await ticketRepository.getAllbyUserAndFavorite();
      emit(TicketFavoriteFetched(ticket));
      return;
    } on Exception catch (e) {
      emit(TicketFetchError(e.toString()));
    }
  }
}
