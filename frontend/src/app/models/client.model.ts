export interface Client {
  id?: number;
  nom: string;
  prenom: string;
  email: string;
  cin:number;
  telephone: string;
  historiqueReservation?: Reservation[];
  // Champs additionnels pour l'affichage
  isActive?: boolean;
  dateInscription?: Date;
  nombreTrajets?: number;
  noteGlobale?: number;
  photo?: string;
  ville?: string;
}

export interface Reservation {
  id?: number;
  clientId: number;
  chauffeurId: number;
  dateReservation: Date;
  dateTrajet: Date;
  heureDepart: string;
  villeDepart: string;
  villeArrivee: string;
  nombrePlaces: number;
  prix: number;
  statut: 'en_attente' | 'confirmee' | 'en_cours' | 'terminee' | 'annulee';
  commentaireClient?: string;
  noteClient?: number;
  noteChauffeur?: number;
}

export interface CreateClientRequest {
  nom: string;
  prenom: string;
  email: string;
  telephone: string;
  cin: string;
  dateNaissance?: Date;
  adresse?: string;
  ville?: string;
  codePostal?: string;
}

export interface UpdateClientRequest extends Partial<CreateClientRequest> {
  statut?: 'actif' | 'inactif' | 'suspendu';
  preferences?: {
    typeVehicule?: string;
    climatisation?: boolean;
    musique?: boolean;
    fumeur?: boolean;
  };
}
