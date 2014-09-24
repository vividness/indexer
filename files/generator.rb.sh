#!/usr/bin/env ruby

random_strings = %w[
  Woodcutter dodo cig rectified munippus unmutilative segner inexpugnably
  nonsymptomatic napoleon nonsubmersible averrhoist unlampooned elegiac harmonica
  mundifying lustring interseminating frons pluralization blastemal molybdous whereas
  notarially gratin heddie antirachitically rationalism sabina Dep collaborationism inquiline
  ungarland subrace unsymptomatic enlister catholicon robot disagreeing immaterialised eusporangiate
  hereditament revacated kursaal messin autoantibody stagier recondition cameras homophyly ruggedly
  interloan whit peplos polycyclic mazy biltong lamina. Caravanned holohedral occasions elverda
  subjugated kurchatovium idiograph expect jetton butyrate nonradiation preconstitute harquebusier
  rainier bridgework cuchulain hofer belittlement hillside nonrepetitious gormandizer bestially
  stylopodia enneastyle plath myoedema cheeky azygospore centerable
]

File.open('input/users.csv', 'w') do |f|
  #generate header
  header  = "id:integer,email:string,"
  header += (1..48).map { |i| "str#{i}:string" }.join(',') + ','
  header += (1..30).map { |i| "int#{i}:integer" }.join(',') + ','
  header += (1..10).map { |i| "float#{i}:float" }.join(',') + ','
  header += (1..5).map  { |i| "datetime#{i}:datetime" }.join(',') + ','
  header += (1..5).map  { |i| "date#{i}:date" }.join(',')

  (1..1_000_000).each do |n|
    record = "#{n},email#{n}@example.org,"
    record += (1..48).map { |i| random_strings.sample }.join(',') + ','
    record += (1..30).map { |i| "#{rand(10000)}" }.join(',') + ','
    record += (1..10).map { |i| "#{rand + rand(9)}" }.join(',') + ','
    record += (1..5).map  { |i| "#{Time.now}" }.join(',') + ','
    record += (1..5).map  { |i| "#{Date.today + rand(9)}" }.join(',')

    f.puts record
  end
end
