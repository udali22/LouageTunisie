export interface Chauffeur {
  id?: number;
  permis: string;
  nom: string;
  prenom: string;
  email: string;
  telephone: string;
  vehiculeMatricule: string;
  numLicence: number;
  cin: number;

  // Champs d'affichage (optionnels)
  isActive?: boolean;
  dateInscription?: Date;
  noteGlobale?: number;
  photo?: string;
  ville?: string;
}
