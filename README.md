Usamos IDE IntelliJ

De inicio, configurar o Lombok e suas dependências no arquivo build.gradle.kts.

#Projeto - Aplicação de transação financeira com POO - **BootCamp Riachuelo**

Esta aplicação é uma simulação de transações financeiras, onde possamos criar uma conta, realizar depositos e saques da conta com chave pix, uma conta de investimentos onde possa ser definido a taxa do investimento e uma aplicação inicial, possibilitando tambem fazer o saque-resgate do investimento. Incluindo tambem a função de histórico das transações realizadas.

Passo 1: Definir as Classes e Modelar o Negócio

Criamos as classes principais do sistema, seguindo os princípios de Programação Orientada a Objetos (POO):

* ```Money:``` Representa uma única unidade monetária, com uma referência a um objeto MoneyAudit para rastrear a origem da transação.

##O Papel e a Importancia dessa classe em nosso projeto.

    Anotações do Lombok:
        @EqualsAndHashCode → Gera automaticamente os métodos equals() e hashCode() para comparação de objetos.
        @ToString → Gera automaticamente o método toString(), útil para depuração e logs.
        @Getter → Gera automaticamente os métodos getters para os atributos (getHistory()).

    Atributo:
        private final MoneyAudit history;
        É imutável (final), ou seja, o valor de history só pode ser definido no construtor.

        MoneyAudit provavelmente é outra classe que guarda informações de auditoria ou histórico das movimentações de dinheiro.

    Construtor:
        Recebe um objeto MoneyAudit e armazena no atributo history.

        *  Encapsula um histórico (MoneyAudit) relacionado a dinheiro — pode ser transações, auditorias ou mudanças de saldo.
        *  Garante imutabilidade do atributo history, o que é importante para segurança e consistência dos dados financeiros.
        *  Facilita manutenção usando Lombok para evitar escrever código repetitivo (getters, equals, hashCode, toString).
        *  Util como um tipo-valor no seu domínio — uma representação conceitual de “dinheiro” no sistema, ligada a auditoria.

* ```MoneyAudit:``` Armazena os metadados de uma transação, como ID, descrição e data.

     Essa classe representa um evento de auditoria relacionado a dinheiro (```MoneyAudit``` → "auditoria de dinheiro").

      Composição:
      1 - UUID transactionId → identificador único da transação.
      2 - BankService targetService → provavelmente indica para qual serviço bancário a transação foi direcionada (deve ser outra classe ou enum no projeto).
      3 - String description → descrição textual da transação (ex.: "Transferência para conta X").
      4 - OffsetDateTime createdAt → data e hora (com fuso horário) em que o registro foi criado.

      Imutável: Por ser record, todos os campos são final e definidos apenas no construtor, garantindo que os dados da transação não mudem depois de criados.
      Identificação única via transactionId, essencial para rastrear operações financeiras.
      Auditabilidade: Contém data/hora (createdAt) e descrição para contextualizar a operação.
      Integração com outros serviços: O targetService sugere que ela está conectada a outros módulos ou microsserviços do sistema bancário.

* ```Wallet:``` Uma classe abstrata que serve como base para todos os tipos de carteira. Contém a lógica de depósito, saque e consulta de fundos.

É a classe principal do projeto, dentro de um sistema financeiro de forma abstrata que vai ser entendida como uma classe "**Pai**", onde serve de modelo para as demais classes subsequentes. Define um contrato comum para todas as carteiras dentro do sistema, tenham as mesmas funcionalidades básicas (um exemplo de herança).
Nesta vamos ter as caracteristicas de Abstração, Encapsulamento.

Funcionalidades:

    * addMoney(List<Money> money): Este método permite adicionar uma lista de objetos Money à carteira. É usado principalmente para transferências entre carteiras.

    * addMoney(long amount, String description): Uma versão mais simples para depósitos diretos. Ele cria uma nova lista de objetos Money com base no valor (amount) e os adiciona à carteira.

    * reduceMoney(long amount): Retira uma quantidade específica de objetos Money da carteira, retornando-os como uma nova lista. É usado para saques ou transferências.

    * getFunds(): Retorna o saldo total da carteira. Em vez de armazenar o saldo como uma variável, o método calcula o valor contando o número de objetos Money na lista.

    * getAllMoney(): Fornece acesso direto à lista de todos os objetos Money na carteira. Isso é útil para funcionalidades como a criação do histórico de transações.
    

* ```AccountWallet:``` Herda de Wallet e representa uma conta bancária com chaves PIX.
* ```InvestmentWallet:``` Herda de Wallet e representa uma carteira de investimento, vinculada a uma conta bancária e a um tipo de investimento.
* ```Investment:``` Representa um tipo de investimento com suas próprias regras, como taxa e valor inicial.

Características Principais das Classes acima:
Herança de Wallet: Ao herdar de Wallet, a AccountWallet já tem acesso a funcionalidades como depositar, sacar e verificar o saldo. Isso evita a duplicação de código e torna a arquitetura do seu projeto mais limpa.

Chaves PIX: O campo pix armazena uma lista de chaves PIX associadas à conta. Esse é um atributo específico de AccountWallet que a diferencia de outros tipos de carteira, como a de investimentos.

Construtores: A classe tem dois construtores para diferentes cenários de criação:

    *   AccountWallet(final List<String> pix): Cria uma conta com um saldo inicial de zero.

    *   AccountWallet(final long amount, final List<String> pix): Cria uma conta com um valor inicial já depositado.

No Vínculo com a Conta e o Investimento: A ```InvestmentWallet``` tem duas referências importantes:

```investment```: O tipo de investimento (por exemplo, um fundo) ao qual esta carteira está associada.

```account```: A conta bancária (AccountWallet) de onde o dinheiro é transferido e para onde os resgates são feitos.

**Também agrega Construtores para diferentes cenários:**

O construtor principal (InvestmentWallet(final Investment investment, ...)): Permite criar uma nova carteira de investimento, transferindo imediatamente um valor inicial da conta bancária para ela.

Um segundo construtor mais genérico (InvestmentWallet(BankService serviceType, ...)): Permite a criação de um objeto sem a necessidade de um depósito inicial.

**Em resumo, a ```InvestmentWallet``` encapsula a lógica de um investimento, diferenciando-se da conta corrente por sua capacidade de gerar rendimentos e por seu vínculo com um tipo de investimento e uma conta bancária.**

##Passo 2: Construir as Classes de Repositório

**Os repositórios são responsáveis por gerenciar e manipular os dados das classes de modelo. Eles atuam como a "camada de dados" do seu aplicativo.**

* ```AccountRepository:``` Gerencia a criação, busca, depósito, saque e transferência entre contas.

* ```InvestmentRepository:``` Gerencia a criação e manipulação de investimentos, além de vincular contas a carteiras de investimento.

* ```CommonsRepository:``` Contém lógicas genéricas compartilhadas, como a validação de fundos.

##Passo 3: Criar a Lógica Principal (* ```Main.java```)

**O arquivo ```Main.java``` é o ponto de entrada do programa, onde a lógica de interação com o usuário é implementada.**

Menu de Opções: Um loop principal com um menu que guia o usuário através de todas as funcionalidades do sistema (criar conta, depositar, transferir, etc.).

Métodos Auxiliares: Funções privadas para cada opção do menu, que chamam os métodos dos repositórios para executar as operações.

Tratamento de Exceções: Uso de blocos try-catch para lidar com erros, como contas não encontradas ou falta de fundos.

##Passo 4: Iterar e Refinar o Código

##Esta foi a etapa mais importante, onde corrigimos uma série de erros de compatibilidade e lógica.

Tipos de Dados: Ajustamos as assinaturas dos métodos em cascata para que a comunicação entre as classes de modelo e de repositório fosse consistente.

Representação de Objetos: Usamos a anotação ```@ToString``` do Lombok de forma mais precisa para que a saída no console ficasse clara e útil, exibindo apenas as informações relevantes.
