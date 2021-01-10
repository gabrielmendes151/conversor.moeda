# conversor.moeda

Projeto criado com o objetivo de fazer a conversão de moeda com base na api da cotação do banco central(<a href="https://olinda.bcb.gov.br/olinda/servico/PTAX/versao/v1/aplicacao#!/recursos/CotacaoMoedaDia#eyJmb3JtdWxhcmlvIjp7IiRmb3JtYXQiOiJqc29uIiwiJHRvcCI6MTAwLCJtb2VkYSI6IlVTRCIsImRhdGFDb3RhY2FvIjoiMDEtMDQtMjAyMSJ9LCJwZXNxdWlzYWRvIjp0cnVlLCJhY3RpdmVUYWIiOiJ0YWJsZSIsImdyaWRTdGF0ZSI6ewMwAzpbewNCAyIEMAQiLANBA30sewNCAyIEMQQiLANBA30sewNCAyIEMgQiLANBA30sewNCAyIEMwQiLANBA30sewNCAyIENAQiLANBA30sewNCAyIENQQiLANBA31dLAMxAzp7fSwDMgM6W10sAzMDOnt9LAM0Azp7fSwDNQM6e319LCJwaXZvdE9wdGlvbnMiOnsDYQM6e30sA2IDOltdLANjAzo1MDAsA2QDOltdLANlAzpbXSwDZgM6W10sA2cDOiJrZXlfYV90b196IiwDaAM6ImtleV9hX3RvX3oiLANpAzp7fSwDagM6e30sA2sDOjg1LANsAzpmYWxzZSwDbQM6e30sA24DOnt9LANvAzoiQ29udGFnZW0iLANwAzoiVGFibGUifX0=">Api do cotação do banco central</a>)

<b>Estrutura do proejeto</b>

Para acesso as api do projeto hospedado no heroku -> <a>https://case-conversor-moeda.herokuapp.com/swagger-ui.html#</a>

Projeto consiste em uma estrutura simples de duas api, uma que traz todos os tipos de moedas disponíveis na api do banco central para
realização da cotação e a outra que faz a conversão do valor de uma moeda para outra.
O projeto possui um sistema de cache para api de conversão de moeda com duração de 30 minutos, após expirado o cache é liberado uma nova
consulta na api criando um novo cache para essa consulta.Esses caches expirados são limpos por um schedule que a cada minuto verifica se 
existem caches expirados no sistema.

<img src="https://i.imgur.com/RXVssWk.png">

<b>Considerações importantes</b>
Não foi criado uma estrutura de fila nessa arquitetura pois não vi sentido nessa funcionalidade,talvez eu tenha entendido de modo errado o que foi proposto.
