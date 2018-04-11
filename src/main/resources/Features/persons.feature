Feature: CRUD Person dan Phone
	Scenario: Penambahan Data Person
        Given Ambil jumlah record person saat ini
        When Ditambahkan record baru dengan first name: Roni, last name: Purwanto, age: 45 years, phone number: 2054913867, no regis: 88888888888
		Then Jumlah record person menjadi jumlah record awal + 1
		
	Scenario: Penambahan Nomor Telepon
		Given Ambil jumlah nomor telepon milik person dengan id 5 saat ini
		When Tambahkan nomor telepon 98785462627 untuk person dengan id 5
		Then Jumlah nomor telepon milik person dengan id 5 menjadi jumlah awal + 1
		
	Scenario: Hapus Data Person
		Given Ambil jumlah record person saat ini
		When Hapus person dengan id 1
		Then Jumlah record person menjadi jumlah record awal - 1