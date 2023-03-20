create table client
(
    identifiant      int auto_increment
        primary key,
    raison_sociale   varchar(255) not null,
    numero_de_rue    varchar(255) not null,
    nom_de_rue       varchar(255) not null,
    code_postal      varchar(255) not null,
    ville            varchar(255) not null,
    telephone        varchar(255) not null,
    adresse_mail     varchar(255) not null,
    commentaires     text         null,
    chiffre_affaires varchar(255) not null,
    nombre_employes  varchar(255) not null,
    constraint raison_sociale
        unique (raison_sociale)
)
    charset = utf8;

create table prospect
(
    identifiant        int auto_increment
        primary key,
    raison_sociale     varchar(255) not null,
    numero_de_rue      varchar(255) not null,
    nom_de_rue         varchar(255) not null,
    code_postal        varchar(255) not null,
    ville              varchar(255) not null,
    telephone          varchar(255) not null,
    adresse_mail       varchar(255) not null,
    commentaires       text         null,
    date_prosprection  date         not null,
    prospect_interesse tinyint(1)   not null,
    constraint raison_sociale
        unique (raison_sociale)
)
    charset = utf8;