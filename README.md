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
