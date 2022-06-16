part of 'ticket_bloc.dart';

@immutable
abstract class TicketState extends Equatable {
  const TicketState();

  @override
  List<Object> get props => [];
}

class TicketInitial extends TicketState {}

class TicketLoadingState extends TicketState {}

class TicketFetched extends TicketState {
  final List<TicketResponse> ticket;

  const TicketFetched(this.ticket);

  @override
  List<Object> get props => [ticket];
}

class TicketFavoriteFetched extends TicketState {
  final List<TicketResponse> ticket;

  const TicketFavoriteFetched(this.ticket);

  @override
  List<Object> get props => [ticket];
}

class TicketFetchError extends TicketState {
  final String message;
  const TicketFetchError(this.message);

  @override
  List<Object> get props => [message];
}
