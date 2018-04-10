Feature: CRUD Person dan Phone
	Scenario: Penambahan Data Person
        Given Ambil jumlah record person saat ini
        When Ditambahkan record baru dengan first name: Roni, last name: Purwanto, age: 45 years, phone number: 2054913867, no regis: 88888888888
		Then Jumlah record person menjadi jumlah record awal + 1