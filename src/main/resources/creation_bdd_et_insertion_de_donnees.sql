DROP DATABASE
    IF EXISTS reverso;
CREATE DATABASE reverso;
use reverso;

create table client
(
    client_identifiant      int auto_increment
        primary key,
    client_raison_sociale   varchar(255) not null,
    client_numero_de_rue    varchar(255) not null,
    client_nom_de_rue       varchar(255) not null,
    client_code_postal      varchar(255) not null,
    client_ville            varchar(255) not null,
    client_telephone        varchar(255) not null,
    client_adresse_mail     varchar(255) not null,
    client_commentaires     text         null,
    client_chiffre_affaires varchar(255) not null,
    client_nombre_employes  varchar(255) not null,
    constraint client_raison_sociale
        unique (client_raison_sociale)
)
    charset = utf8mb3;

create table contrat
(
    identifiant_contrat int auto_increment
        primary key,
    identifiant_client  int          not null,
    libelle             varchar(255) not null,
    montant             double       not null,
    constraint fk_client_contrat
        foreign key (identifiant_client) references client (client_identifiant) ON DELETE CASCADE
);

create table prospect
(
    prospect_identifiant       int auto_increment
        primary key,
    prospect_raison_sociale    varchar(255) not null,
    prospect_numero_de_rue     varchar(255) not null,
    prospect_nom_de_rue        varchar(255) not null,
    prospect_code_postal       varchar(255) not null,
    prospect_ville             varchar(255) not null,
    prospect_telephone         varchar(255) not null,
    prospect_adresse_mail      varchar(255) not null,
    prospect_commentaires      text         null,
    prospect_date_prosprection date         not null,
    prospect_interesse         char(3)      not null,
    constraint prospect_raison_sociale
        unique (prospect_raison_sociale)
)
    charset = utf8mb3;



INSERT INTO client (client_raison_sociale, client_numero_de_rue, client_nom_de_rue, client_code_postal, client_ville,
                    client_telephone,
                    client_adresse_mail,
                    client_commentaires, client_chiffre_affaires, client_nombre_employes)
VALUES ('MySQL Client n°1', '123 Main St', 'Suite 100', '75001', 'Paris', '01 23 45 67 89', 'contact@abccompany.com',
        NULL,
        '500000', '20'),
       ('MySQL Client n°2', '456 Broadway', 'Floor 3', '69002', 'Lyon', '04 56 78 90 12', 'contact@xyzcorp.com',
        'Client fidèle depuis 5 ans', '1000000', '50');

INSERT INTO prospect (prospect_raison_sociale, prospect_numero_de_rue, prospect_nom_de_rue, prospect_code_postal,
                      prospect_ville,
                      prospect_telephone,
                      prospect_adresse_mail,
                      prospect_commentaires, prospect_date_prosprection, prospect_interesse)
VALUES ('MySQL Prospect n°1', '789 5th Ave', 'Suite 200', '06000', 'Nice', '04 12 34 56 78', 'contact@acmeinc.com',
        NULL,
        '2023-03-01', 'Non'),
       ('MySQL Prospect n°2', '10 Downing St', 'Floor 4', '33000', 'Bordeaux', '05 67 89 01 23',
        'contact@globexcorp.com',
        'Intéressé par notre nouveau produit', '2023-03-15', 'Oui');

INSERT INTO contrat (identifiant_client, libelle, montant)
VALUES (1, 'MySQL - Contrat n°1 avec le client n°1', 444.44),
       (1, 'MySQL - Contrat n°2 avec le client n°1', 444.44),
       (2, 'MySQL - Contrat n°3 avec le client n°2', 444.44),
       (2, 'MySQL - Contrat n°4 avec le client n°2', 999.99);