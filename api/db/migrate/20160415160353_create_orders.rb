class CreateOrders < ActiveRecord::Migration
  def change
    create_table :orders, id: false do |t|
      t.primary_key :ExternalOrderNo
      t.integer :ProductSN
      t.string :StyleA
      t.string :StyleB
      t.integer :Quantity
      t.integer :Price
      t.integer :Amount
      t.string :OrderName
      t.string :OrderAddress
      t.string :OrderEmail
      t.string :OrderPhone
      t.string :ConsigneeName
      t.string :ConsigneeAddress
      t.string :ConsigneeEmail
      t.string :ConsigneePhone
      t.string :DeliverTime
      t.integer :Result
      t.integer :PaymentResult
      t.text :Param

      t.timestamps null: false
    end
  end
end
