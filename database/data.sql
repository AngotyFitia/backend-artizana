INSERT INTO Utilisateur(nom, prenom, email, mot_de_passe, etat) 
            VALUES  ('ANDRIA', 'Nancy', 'nancyandriamahanintsoa@gmail.com', 'nancy', 1), -- admin
                    ('RABARI', 'Angoty', 'angotyrabarijaona@gmail.com', 'angoty', 2), -- artisan
                    ('RAKOTO', 'Anne', 'anne@gmail.com', 'anne', 3); -- client

INSERT INTO Categorie(intitule, etat) 
            VALUES  ('Haingon-trano sy zava-kanto', 1), ('Poketra', 1), ('Rojo sy ravaka', 1), ('Sary sokitra', 1);

INSERT INTO Societe(id_utilisateur, nom, titre, histoire, photo, video, etat) 
    VALUES  (2, 'MalagasyVita', 'Mpanao haingon-trano sy zava-kanto nentim-paharazana', 'KANTONAY dia orinasa natsangana tamin''ny fitiavana ny zavakanto sy ny kolontsaina Malagasy. Mampiasa akora voajanahary izy ireo hanamboarana haingon-trano, toy ny hazo sy harona vita tanana.', 'kantonay.jpg', 'https://youtu.be/kantonayvideo', 1),
            (2, 'TANTELY', 'Asa tanana vita amin''ny landy sy kofehy', 'TANTELY dia niandoha avy amin''ny fianakaviana mpanao asa tanana nandritra ny taranaka maro. Ny vokatra ataony dia manambatra ny fomba nentim-paharazana sy ny lamaody ankehitriny.', 'tantely.png', 'https://youtu.be/tantelycraft',1);

INSERT INTO Prix_produit(id_produit, id_prix) VALUES



INSERT INTO Societe (id_utilisateur, nom, titre, histoire, photo, video, etat)
VALUES (
    2,
    'Artizana SARL',
    'Valorisation de l’Artisanat Malgache',
    'Artizana SARL a été créée en 2018 avec l''ambition de donner une voix aux artisans malgaches et de promouvoir leurs créations sur les marchés nationaux et internationaux. L’entreprise a commencé avec une petite équipe passionnée de design et de commerce, travaillant directement avec des artisans locaux pour comprendre leurs besoins et les aider à améliorer la qualité de leurs produits. Au fil des années, Artizana SARL a mis en place plusieurs programmes de formation, apportant non seulement des compétences techniques mais aussi des connaissances en gestion, marketing et commerce électronique. L’entreprise a également développé une plateforme numérique permettant aux artisans de vendre leurs produits directement aux clients, réduisant ainsi les intermédiaires et augmentant leur revenu. Chaque produit raconte une histoire unique, de la sélection des matières premières à la création finale, reflétant l’héritage culturel et le savoir-faire traditionnel. Grâce à ces efforts, Artizana SARL a réussi à créer un réseau solide d’artisans, à participer à des foires internationales et à faire reconnaître l’artisanat malgache dans plusieurs pays.',
    pg_read_binary_file('D:/Me/Sary/Angoty.jpeg'),
    pg_read_binary_file('C:/Users/Nancy Hanintsoa/Videos/Captures/Countdown Birthday timer 10 second Happy birthday wishes - YouTube - Opera 2025-09-11 08-02-54.mp4'),
    0
);



INSERT INTO Facture (id_utilisateur, id_societe, "date", total, etat)
VALUES (
    3,                     
    2,                     
    CURRENT_DATE,          
    15000,                
    0                      
);

