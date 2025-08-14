Usamos IDE IntelliJ

De inicio, configurar o Lombok e suas dependências no arquivo build.gradle.kts.

#Projeto - Aplicação de transação financeira com POO - BootCamp Riachuelo

Esta aplicação é uma simulação de transações financeiras, onde possamos criar uma conta, realizar depositos e saques da conta com chave pix, uma conta de investimentos onde possa ser definido a taxa do investimento e uma aplicação inicial, possibilitando tambem fazer o saque-resgate do investimento. Incluindo tambem a função de histórico das transações realizadas.

##Passo 1: Definir as Classes e Modelar o Negócio

##Criamos as classes principais do sistema, seguindo os princípios de Programação Orientada a Objetos (POO):

* ```Money:``` Representa uma única unidade monetária, com uma referência a um objeto MoneyAudit para rastrear a origem da transação.

* ```MoneyAudit:``` Armazena os metadados de uma transação, como ID, descrição e data.

* ```Wallet:``` Uma classe abstrata que serve como base para todos os tipos de carteira. Contém a lógica de depósito, saque e consulta de fundos.

* ```AccountWallet:``` Herda de Wallet e representa uma conta bancária com chaves PIX.

* ```InvestmentWallet:``` Herda de Wallet e representa uma carteira de investimento, vinculada a uma conta bancária e a um tipo de investimento .

* ```Investment:``` Representa um tipo de investimento com suas próprias regras, como taxa e valor inicial.


#Passo 2: Construir as Classes de Repositório

##Os repositórios são responsáveis por gerenciar e manipular os dados das classes de modelo. Eles atuam como a "camada de dados" do seu aplicativo.

AccountRepository: Gerencia a criação, busca, depósito, saque e transferência entre contas.

InvestmentRepository: Gerencia a criação e manipulação de investimentos, além de vincular contas a carteiras de investimento.

CommonsRepository: Contém lógicas genéricas compartilhadas, como a validação de fundos.


#Passo 3: Criar a Lógica Principal (Main.java)

##O arquivo Main.java é o ponto de entrada do programa, onde a lógica de interação com o usuário é implementada.

Menu de Opções: Um loop principal com um menu que guia o usuário através de todas as funcionalidades do sistema (criar conta, depositar, transferir, etc.).

Métodos Auxiliares: Funções privadas para cada opção do menu, que chamam os métodos dos repositórios para executar as operações.

Tratamento de Exceções: Uso de blocos try-catch para lidar com erros, como contas não encontradas ou falta de fundos.

#Passo 4: Iterar e Refinar o Código

##Esta foi a etapa mais importante, onde corrigimos uma série de erros de compatibilidade e lógica.

Tipos de Dados: Ajustamos as assinaturas dos métodos em cascata para que a comunicação entre as classes de modelo e de repositório fosse consistente.

Representação de Objetos: Usamos a anotação @ToString do Lombok de forma mais precisa para que a saída no console ficasse clara e útil, exibindo apenas as informações relevantes.

Lógica de Histórico: Corrigimos a forma como o histórico era coletado e exibido, agrupando as transações para mostrar o valor total.
