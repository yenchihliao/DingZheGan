class Company < ActiveRecord::Base
  has_many :products

  validates_numericality_of :number
  validates_presence_of :name
end
