INSERT INTO Utilisateur(nom, prenom, email, mot_de_passe, etat) 
            VALUES  ('ANDRIA', 'Nancy', 'nancyandriamahanintsoa@gmail.com', 'nancy', 1), -- admin
                    ('RABARI', 'Angoty', 'angotyrabarijaona@gmail.com', 'angoty', 2), -- artisan
                    ('RAKOTO', 'Anne', 'anne@gmail.com', 'anne', 3); -- client

INSERT INTO Categorie(intitule, etat) 
            VALUES  ('Haingon-trano sy zava-kanto', 1), ('Poketra', 1), ('Rojo sy ravaka', 1), ('Sary sokitra', 1);

INSERT INTO Societe(id_utilisateur, nom, titre, histoire, photo, video, etat) 
    VALUES  (2, 'KANTONAY', 'Mpanao haingon-trano sy zava-kanto nentim-paharazana', 'KANTONAY dia orinasa natsangana tamin''ny fitiavana ny zavakanto sy ny kolontsaina Malagasy. Mampiasa akora voajanahary izy ireo hanamboarana haingon-trano, toy ny hazo sy harona vita tanana.', 'kantonay.jpg', 'https://youtu.be/kantonayvideo', 1),
            (2, 'TANTELY', 'Asa tanana vita amin''ny landy sy kofehy', 'TANTELY dia niandoha avy amin''ny fianakaviana mpanao asa tanana nandritra ny taranaka maro. Ny vokatra ataony dia manambatra ny fomba nentim-paharazana sy ny lamaody ankehitriny.', 'tantely.png', 'https://youtu.be/tantelycraft',1);
