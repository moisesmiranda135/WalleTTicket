part of 'ticket_bloc.dart';

@immutable
abstract class TicketEvent extends Equatable {
  const TicketEvent();

  @override
  List<Object> get props => [];
}

class FetchTicketByUser extends TicketEvent {
  const FetchTicketByUser();

  @override
  List<Object> get props => [];
}
