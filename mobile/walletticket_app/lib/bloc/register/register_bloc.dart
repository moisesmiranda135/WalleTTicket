import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:walletticket_app/models/register/register_dto.dart';
import 'package:walletticket_app/models/register/register_response.dart';
import 'package:walletticket_app/repository/auth_repository/auth_repository.dart';

part 'register_event.dart';
part 'register_state.dart';

class RegisterBloc extends Bloc<RegisterEvent, RegisterState> {
  final AuthRepository authRepository;

  RegisterBloc(this.authRepository) : super(RegisterInitialState()) {
    on<DoRegisterEvent>(_doRegisterEvent);
  }

  void _doRegisterEvent(
      DoRegisterEvent event, Emitter<RegisterState> emit) async {
    try {
      final registerResponse = await authRepository.register(event.registerDto);
      emit(RegisterSuccessState(registerResponse));
      return;
    } on Exception catch (e) {
      emit(RegisterErrorState(e.toString()));
    }
  }
}
