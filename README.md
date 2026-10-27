# TP4 - Sockets en mode non-connecté UDP (Chat)

**Étudiant :** Chayma Bouassida  
**Matière :** Développement d'applications réparties  
**Année universitaire :** 2026 – 2027

Ce projet implémente un système de chat client-serveur basé sur le protocole **UDP** (mode non-connecté) en Java. Le développement est structuré en trois étapes progressives : de l'envoi simple, vers une communication bidirectionnelle, puis vers un système de diffusion (Broadcast) multi-clients.

---

# Étape 1 : Chat Unidirectionnel

## Objectif
Développer un serveur qui écoute sur le port 1234 et un client qui envoie des messages préfixés par son nom d'utilisateur. Le serveur affiche le message avec l'adresse IP et le port.

## Code Source
### Server_4_1.java
![Code Server 4.1](screenshots/code_server_4_1.png)

### Client_4_1.java
![Code Client 4.1](screenshots/code_client_4_1.png)

## Exécution et Tests
Démarrage du serveur en attente sur le port 1234.
![Serveur en attente](screenshots/exec_server_wait_4_1.png)

Le client `chayma` envoie un message.
![Client envoie message](screenshots/exec_client_send_4_1.png)

Le serveur reçoit le message avec l'IP et le port.
![Serveur reçoit message](screenshots/exec_server_recv_4_1.png)

Le client tape `quit` et s'arrête.
![Client quitte](screenshots/exec_client_quit_4_1.png)

---

# Étape 2 : Chat Bidirectionnel 1-à-1

## Objectif
Le serveur ne fait plus qu'écouter, il **répond** au client. Pour que le client puisse recevoir la réponse du serveur en temps réel sans être bloqué, nous introduisons un **Thread de réception**.

## Code Source
### Server_4_2.java
Le serveur reçoit le message et renvoie une réponse à l'expéditeur.
![Code Server 4.2](screenshots/code_server_4_2.png)

### Client_4_2.java (avec Thread)
Le client utilise une classe anonyme `Runnable` pour écouter le serveur en arrière-plan.
![Code Client 4.2](screenshots/code_client_4_2.png)

## Exécution et Tests
Démarrage du serveur 4.2.
![Serveur 4.2 démarre](screenshots/exec_server_start_4_2.png)

Le serveur reçoit le message et renvoie une confirmation.
![Serveur 4.2 reçoit](screenshots/exec_server_4_2.png)

Le client reçoit la réponse du serveur tout en gardant l'invite `>` active (grâce au Thread).
![Client 4.2 reçoit](screenshots/exec_client_4_2.png)

---

# Étape 3 : Chat Multi-clients et Diffusion

## Objectif
Permettre à plusieurs clients de discuter ensemble. Le serveur maintient une liste des adresses des clients et **diffuse (broadcast)** chaque message reçu à tous les autres clients.

## Code Source
### Server_4_3.java
Le serveur garde une liste de `SocketAddress` et fait le routeur.
![Code Server 4.3](screenshots/code_server_4_3.png)

### Client_4_3.java (avec Thread et message de connexion)
Le client utilise un Thread et envoie un message "Join" automatique dès son lancement.
![Code Client 4.3](screenshots/code_client_4_3.png)

## Configuration pour les tests locaux
Activation de l'option **"Allow multiple instances"** dans IntelliJ pour lancer plusieurs clients sur la même machine.
![Configuration Multiple Instances](screenshots/config_multiple_instances.png)

## Exécution et Tests

### 1. Démarrage du serveur
![Serveur 4.3 démarre](screenshots/exec_server_start_4_3.png)

### 2. Connexions des clients (Message "Join")
**Alice** rejoint le chat.

![Alice connectée](screenshots/exec_alice_connect.png)

Le serveur enregistre Alice.

![Serveur voit Alice](screenshots/exec_server_alice.png)

**Bob** rejoint le chat.

![Bob connecté](screenshots/exec_bob_connect.png)

Le serveur enregistre Bob.

![Serveur voit Bob](screenshots/exec_server_bob.png)


**Charlie** rejoint le chat.

![Charlie connecté](screenshots/exec_charlie_connect.png)

Le serveur enregistre Charlie.

![Serveur voit Charlie](screenshots/exec_server_charlie.png)

### 3. Diffusion des notifications
Alice est notifiée quand Bob rejoint.

![Alice voit Bob](screenshots/exec_alice_sees_bob.png)

Alice est notifiée quand Charlie rejoint.

![Alice voit Charlie](screenshots/exec_alice_sees_charlie.png)

Bob est notifié quand Charlie rejoint.

![Bob voit Charlie](screenshots/exec_bob_sees_charlie.png)

### 4. Diffusion d'un message de chat
Alice envoie "Salut tout le monde !".

![Alice envoie](screenshots/exec_alice_sends.png)

Bob reçoit le message en temps réel.

![Bob reçoit](screenshots/exec_bob_receives.png)

Charlie reçoit le message en temps réel.

![Charlie reçoit](screenshots/exec_charlie_receives.png)

### 5. Logs complets du Serveur (Preuve finale)
La console du serveur montre l'enregistrement des 3 clients et la diffusion du message.

![Logs complets du serveur](screenshots/exec_server_full_logs.png)

## Synthèse
1. **UDP vs TCP :** UDP étant non-connecté, le serveur ne détecte pas l'arrivée d'un client. Le client envoie donc un message "Join" automatique au démarrage pour s'enregistrer.
2. **Diffusion :** Le serveur utilise une boucle `for` pour rediriger les messages vers tous les autres clients connectés (excepté l'expéditeur).
3. **Threads :** Le client utilise un Thread (`Runnable`) pour écouter en arrière-plan, rendant la réception non-bloquante pendant la saisie.
