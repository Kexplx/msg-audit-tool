import { Factor } from './models/factor.model';

export const factors: Factor[] = [
  {
    title: '1. Effektivität',
    criterias: [
      {
        title: '1. Effektivität',
        questions: ['Question 1?', 'Question 2?', 'Question 3?', 'Question 4?', 'Question 5?'],
      },
    ],
  },
  {
    title: '2. Effizienz',
    criterias: [
      {
        title: '2. Effizienz',
        questions: ['Question 1?', 'Question 2?', 'Question 3?', 'Question 4?', 'Question 5?'],
      },
    ],
  },
  {
    title: '3. Zufriedenheit',
    criterias: [
      {
        title: '3.1 Nützlichkeit',
        questions: ['Question 1?', 'Question 2?', 'Question 3?', 'Question 4?', 'Question 5?'],
      },
      {
        title: '3.2 Vertrauen',
        questions: ['Question 1?', 'Question 2?', 'Question 3?', 'Question 4?', 'Question 5?'],
      },
      {
        title: '3.3 Wohlgefälligkeit',
        questions: ['Question 1?', 'Question 2?', 'Question 3?', 'Question 4?', 'Question 5?'],
      },
      {
        title: '3.4 Komfort',
        questions: ['Question 1?', 'Question 2?', 'Question 3?', 'Question 4?', 'Question 5?'],
      },
    ],
  },
  {
    title: '4. Risikofreiheit',
    criterias: [
      { title: '4.1 Verringerung der ökonomischen Risiken' },
      { title: '4.2 Verringerung der Risiken hinsichtlich Gesundheit und Sicherheit' },
      { title: '4.3 Verringerung der Umweltrisiken' },
    ],
  },
  {
    title: '5. Lieferbestandteile',
    criterias: [
      { title: '5.1 Komplette Abdeckung aller Umgebungsanforderungen' },
      { title: '5.2 Flexibilität' },
    ],
  },
  {
    title: '6. Funktionale Tauglichkeit',
    criterias: [
      { title: '6.1 Funktionale Vollständigkeit' },
      { title: '6.2 Funktionale Richtigkeit' },
      { title: '6.3 Funktionale Angemessenheit' },
    ],
  },
  {
    title: '7. Performanz, Effizienz',
    criterias: [
      { title: '7.1 Antwortzeitverhalten' },
      { title: '7.2 Ressourcenverbrauch' },
      { title: '7.3 Kapazität' },
    ],
  },
  {
    title: '8. Austauschbarkeit, Übertragbarkeit',
    criterias: [{ title: '8.1 Koexistenz' }, { title: '8.2 Interoperabilität' }],
  },
  {
    title: '9. Benutzbarkeit',
    criterias: [
      { title: '9.1 Angemessenheit Erkennbarkeit' },
      { title: '9.2 Erlernbarkeit' },
      { title: '9.3 Bedienbarkeit' },
      { title: '9.4 auf Anwender bezogene Fehlervorbeugung' },
      { title: '9.5 Ästhetik der Benutzeroberfläche' },
      { title: '9.6 Zugänglichkeit/Barrierefreiheit' },
    ],
  },
  {
    title: '10. Zuverlässigkeit',
    criterias: [
      { title: '10.1 Reife' },
      { title: '10.2 Verfügbarkeit' },
      { title: '10.3 Fehlertoleranz' },
      { title: '10.4 Wiederherstellbarkeit' },
    ],
  },
  {
    title: '11. Sicherheit',
    criterias: [
      { title: '11.1 Vertraulichkeit, Zugriffsrechte' },
      { title: '11.2 Integrität' },
      { title: '11.3 Nachweisbarkeit, Schutz vor fremden Zugriff' },
      { title: '11.4 Haftung' },
      { title: '11.5 Authentizität, Glaubwürdigkeit' },
    ],
  },
  {
    title: '12. Wartbarkeit',
    criterias: [
      { title: '12.1 Modularität' },
      { title: '12.2 Wiederverwendbarkeit' },
      { title: '12.3 Analysierbarkeit' },
      { title: '12.4 Modifizierbarkeit' },
      { title: '12.5 Prüfbarkeit' },
    ],
  },
  {
    title: '13. Portabilität',
    criterias: [
      { title: '13.1 Anpassbarkeit' },
      { title: '13.1 Installierbarkeit' },
      { title: '13.1 Austauschbarkeit' },
    ],
  },
  { title: '14. Sonstige Anforderungen', criterias: [{ title: '14. Sonstige Anforderungen' }] },
];
