# language: pt
Funcionalidade: Reservar uma mesa no restaurante
  Como um cliente
  Eu quero reservar uma mesa no restaurante
  Para que eu possa ter um lugar garantido

  Cenário: Reservar uma mesa com sucesso
    Dado que o restaurante tem uma mesa disponível
    Quando eu reservo a mesa
    Então a reserva é feita com sucesso

  Cenário: Reservar uma mesa sem sucesso
    Dado que o restaurante nao tem uma mesa disponível
    Quando eu tento reservar a mesa
    Então a reserva não é feita
