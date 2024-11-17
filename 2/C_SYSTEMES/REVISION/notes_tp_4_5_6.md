# Guide des appels système UNIX

## 1. open

**Utilité** : Ouvre un fichier pour lecture, écriture ou les deux.

**Prototype** :
```c
int open(const char *pathname, int flags, mode_t mode);
```

**Arguments** :
- `pathname` : Chemin du fichier
- `flags` : Mode d'ouverture (O_RDONLY, O_WRONLY, O_RDWR, O_CREAT, O_TRUNC, etc.)
- `mode` : Permissions (utilisé avec O_CREAT, par ex. 0644)

**Retour** : Descripteur de fichier (int) ou -1 en cas d'erreur

**Bibliothèque** : `<fcntl.h>`, `<unistd.h>`

**Exemple** :
```c
int fd = open("file.txt", O_WRONLY | O_CREAT | O_TRUNC, 0644);
```

## 2. dup2

**Utilité** : Redirige un descripteur de fichier vers un autre.

**Prototype** :
```c
int dup2(int oldfd, int newfd);
```

**Arguments** :
- `oldfd` : Descripteur existant
- `newfd` : Nouveau descripteur

**Retour** : Nouveau descripteur ou -1 en cas d'erreur

**Bibliothèque** : `<unistd.h>`

**Exemple** :
```c
dup2(fd, STDOUT_FILENO); // Redirige stdout vers le fichier ouvert par fd
```

## 3. pipe

**Utilité** : Crée un tube unidirectionnel pour la communication entre processus.

**Prototype** :
```c
int pipe(int pipefd[2]);
```

**Arguments** :
- `pipefd` : Tableau de deux descripteurs de fichier (pipefd[0] pour lecture, pipefd[1] pour écriture)

**Retour** : 0 en cas de succès, -1 en cas d'échec

**Bibliothèque** : `<unistd.h>`

**Exemple** :
```c
int pipefd[2];
pipe(pipefd);
```

## 4. fork

**Utilité** : Crée un nouveau processus.

**Prototype** :
```c
pid_t fork(void);
```

**Retour** :
- > 0 : PID du processus enfant dans le parent
- 0 : Dans l'enfant
- -1 : En cas d'erreur

**Bibliothèque** : `<unistd.h>`

**Exemple** :
```c
pid_t pid = fork();
```

## 5. exec

**Utilité** : Remplace l'image d'un processus par une autre.

**Prototypes principaux** :
```c
int execlp(const char *file, const char *arg, ..., NULL);
int execvp(const char *file, char *const argv[]);
```

**Arguments** :
- `file` : Nom ou chemin de l'exécutable
- `arg` : Arguments passés au programme, terminés par NULL

**Retour** : Ne retourne jamais si réussi, -1 en cas d'échec

**Bibliothèque** : `<unistd.h>`

**Exemple** :
```c
execlp("ls", "ls", "-l", NULL);
```

## 6. wait et waitpid

**Utilité** : Attend la terminaison d'un processus enfant.

**Prototype** :
```c
pid_t wait(int *wstatus);
pid_t waitpid(pid_t pid, int *wstatus, int options);
```

**Arguments** :
- `wstatus` : Permet de récupérer le statut de retour
- `pid` : PID d'un processus spécifique (waitpid)
- `options` : Options pour le comportement (ex. WNOHANG)

**Bibliothèque** : `<sys/wait.h>`

**Exemple** :
```c
int status;
wait(&status);
```

## 7. scanf et printf

**Utilité** : Entrée/sortie formatée dans les programmes.

**Prototypes** :
```c
int scanf(const char *format, ...);
int printf(const char *format, ...);
```

**Arguments** :
- `format` : Chaîne formatée spécifiant le type de données

**Bibliothèque** : `<stdio.h>`

**Exemple** :
```c
int a, b;
scanf("%d %d", &a, &b);
printf("%d\n", a + b);
```

## 8. Signaux (signal)

**Utilité** : Gère les signaux entre processus.

**Prototype** :
```c
void (**signal(int signum, void (**handler)(int)))(int);
```

**Arguments** :
- `signum` : Numéro du signal (ex. SIGCHLD, SIGPIPE)
- `handler` : Fonction de gestion du signal

**Bibliothèque** : `<signal.h>`

**Exemple** :
```c
signal(SIGCHLD, handler);
```

## Exercices correspondants

### Redirection
- Fonctions : open, dup2, close
- Exemple : Rediriger stdout pour écrire dans un fichier

### Fork + communication via pipes
- Fonctions : fork, pipe, dup2, wait
- Exemple : Parent/Enfant avec communication

### Exec
- Fonctions : execlp, fork, dup2
- Exemple : Exécution de commandes avec redirection

### Signaux
- Fonctions : signal, SIGCHLD
- Exemple : Détecter la fin d'un enfant

## Bibliothèques à inclure
- `<fcntl.h>` : Pour open
- `<unistd.h>` : Pour fork, pipe, dup2, etc.
- `<stdio.h>` : Pour printf, scanf, etc.
- `<sys/wait.h>` : Pour wait, waitpid
- `<signal.h>` : Pour gestion des signaux
